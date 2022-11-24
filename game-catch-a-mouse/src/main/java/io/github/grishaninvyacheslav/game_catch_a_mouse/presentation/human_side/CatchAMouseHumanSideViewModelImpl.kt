package io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.human_side

import android.view.MotionEvent
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.CoordsEntity
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.MouseEntity
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.PlaygroundEntity
import io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.CatchAMouseViewModel
import kotlin.math.abs
import kotlin.math.atan2

class CatchAMouseHumanSideViewModelImpl(
    private val database: FirebaseDatabase
) : ViewModel(), CatchAMouseViewModel {
    private val mutablePlaygroundState: MutableLiveData<PlaygroundState> = MutableLiveData()
    val playgroundState: LiveData<PlaygroundState>
        get() {
            if (mutablePlaygroundState.value != null) {
                return mutablePlaygroundState
            }
            return mutablePlaygroundState.apply {
                mutablePlaygroundState.value = PlaygroundState.Loading
                database.getReference("playground_1").child("catch_a_mouse")
                    .addValueEventListener(object : ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            val value = snapshot.getValue<PlaygroundEntity>()
                            value?.source_screen_size?.let {
                                it.width?.let { fullSizeWidth = it }
                                it.height?.let { fullSizeHeight = it }
                            } ?: run {
                                return
                            }
                            value.mouses_list?.let { remoteMouseEntities ->
                                mousesList.forEach { localMouseEntity ->
                                    if (!remoteMouseEntities.keys.contains(localMouseEntity.id)) {
                                        mousesList.remove(localMouseEntity)
                                    }
                                }
                            } ?: run {
                                mousesList.clear()
                            }
                            if (mutablePlaygroundState.value != PlaygroundState.Success) {
                                mutablePlaygroundState.value = PlaygroundState.Success
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            mutablePlaygroundState.value =
                                PlaygroundState.Error(error.toException())
                        }
                    })
            }
        }

    override val mousesList = mutableStateListOf<MouseEntity>()
    override val widthScale = mutableStateOf(1F)
    override val heightScale = mutableStateOf(1F)

    override fun setLocationOffset(offset: Offset) {
        locationOffset = offset
    }

    override fun setMouseCollisionSize(widthPx: Float, heightPx: Float) {
        mouseWidthPx = widthPx
        mouseHeightPx = heightPx
    }

    override fun setMouseRotationDeltaThresholdPx(thresholdPx: Float) {
        mouseRotationDeltaThresholdPx = thresholdPx
    }

    override fun onViewSizeChanged(size: IntSize, dpSizePx: Float) {
        this.dpSizePx = dpSizePx
        widthScale.value = fullSizeWidth / (size.width / dpSizePx)
        heightScale.value = fullSizeHeight / (size.height / dpSizePx)
        playgroundSize.value = size
        mouseHoleRect.value = Rect(
            Offset((size.width / 2).toFloat(), (size.height / 2).toFloat()),
            if (mouseWidthPx / 2F < MIN_DRAG_BOX_SIZE_DP * dpSizePx) {
                MIN_DRAG_BOX_SIZE_DP * dpSizePx / 2F
            } else {
                mouseWidthPx / 2F
            }

        )
    }

    override fun touchDown(motionEvent: MotionEvent) {
        mousesList.forEach { mouseEntity ->
            if (isPointInsideRect(
                    pointX = motionEvent.x,
                    pointY = motionEvent.y,
                    rectX =
                    if (mouseEntity.width < MIN_DRAG_BOX_SIZE_DP * dpSizePx) {
                        mouseEntity.x + mouseEntity.width / 2 - MIN_DRAG_BOX_SIZE_DP / 2 * dpSizePx
                    } else {
                        mouseEntity.x
                    },
                    rectY =
                    if (mouseEntity.height < MIN_DRAG_BOX_SIZE_DP * dpSizePx) {
                        mouseEntity.y + mouseEntity.height / 2 - MIN_DRAG_BOX_SIZE_DP / 2 * dpSizePx
                    } else {
                        mouseEntity.y
                    },
                    rectWidth =
                    if (mouseEntity.width < MIN_DRAG_BOX_SIZE_DP * dpSizePx) {
                        MIN_DRAG_BOX_SIZE_DP * dpSizePx
                    } else {
                        mouseEntity.width
                    },
                    rectHeight =
                    if (mouseEntity.height < MIN_DRAG_BOX_SIZE_DP * dpSizePx) {
                        MIN_DRAG_BOX_SIZE_DP * dpSizePx
                    } else {
                        mouseEntity.height
                    }
                )
            ) {
                pickMouseToDrag(mouseEntity, motionEvent.x, motionEvent.y)
                return
            }
        }
        if (isPointInsideRect(motionEvent.x, motionEvent.y, mouseHoleRect.value)) {
            addMouse(motionEvent.x, motionEvent.y)
        }
    }

    override fun touchMove(motionEvent: MotionEvent) {
        val currentMouseToDragIndex =
            mousesList.indexOfFirst { it.id == currentObjectToDragId.value }
        if (currentMouseToDragIndex == -1) {
            return
        }
        val currentMouseToDrag = mousesList[currentMouseToDragIndex]
        val updatedMouseEntity =
            updateMouseEntity(currentMouseToDrag, motionEvent.x, motionEvent.y)
        mousesList[currentMouseToDragIndex] = updatedMouseEntity
        sendRemoteMouseCoords(
            updatedMouseEntity.id,
            CoordsEntity(updatedMouseEntity.x, updatedMouseEntity.y, updatedMouseEntity.zIndex),
        )
    }

    override fun touchUp(motionEvent: MotionEvent) {
        val currentMouseToDrag =
            mousesList.find { it.id == currentObjectToDragId.value }
        if (currentMouseToDrag?.zIndex == 0f) {
            mousesList.remove(currentMouseToDrag)
            removeRemoteMouse(currentMouseToDrag.id)
            currentObjectToDragId.value = null
        }
        currentObjectToDragId.value = null
    }

    private val MIN_DRAG_BOX_SIZE_DP = 50
    private var dpSizePx = 1F
    private var fullSizeWidth = 1200F
    private var fullSizeHeight = 1920F
    private var locationOffset = Offset(0F, 0F)
    private var mouseWidthPx = 0F
    private var mouseHeightPx = 0F
    private var mouseRotationDeltaThresholdPx = 0F
    private val dragOffset = mutableStateOf(Offset(0F, 0F))
    private val mouseHoleRect = mutableStateOf(Rect(Offset(0F, 0F), 0F))
    private val playgroundSize = mutableStateOf(IntSize.Zero)
    private val currentObjectToDragId: MutableState<String?> = mutableStateOf(null)

    private fun sendRemoteMouseCoords(mouseId: String, coordsEntity: CoordsEntity) {
        val myRef =
            database.getReference("playground_1").child("catch_a_mouse").child("mouses_list")
                .child(mouseId)
        myRef.setValue(coordsEntity.apply {
            x = x / dpSizePx * widthScale.value
            y = y / dpSizePx * heightScale.value
        })
    }

    private fun removeRemoteMouse(mouseId: String) {
        val myRef =
            database.getReference("playground_1").child("catch_a_mouse").child("mouses_list")
                .child(mouseId)
        myRef.removeValue()
    }

    private fun pickMouseToDrag(mouseEntity: MouseEntity, touchEventX: Float, touchEventY: Float) {
        currentObjectToDragId.value = mouseEntity.id
        dragOffset.value = Offset(
            touchEventX - mouseEntity.x,
            touchEventY - mouseEntity.y
        )
    }

    private fun getAngleIfExceedThreshold(
        fromX: Float,
        fromY: Float,
        toX: Float,
        toY: Float,
        threshold: Float
    ): Double? {
        val dx: Double = (toX - fromX).toDouble()
        val dy: Double = (fromY - toY).toDouble()
        if (abs(dx) < threshold && abs(dy) < threshold) {
            return null
        }
        var inRads = atan2(dy, dx)
        inRads = if (inRads < 0) abs(inRads) else 2 * Math.PI - inRads
        return Math.toDegrees(inRads) + 90.0
    }

    private fun isPointInsideRect(pointX: Float, pointY: Float, rect: Rect) =
        pointX > rect.left && pointX < rect.right &&
                pointY > rect.top && pointY < rect.bottom

    private fun isPointInsideRect(
        pointX: Float,
        pointY: Float,
        rectX: Float,
        rectY: Float,
        rectWidth: Float,
        rectHeight: Float
    ) =
        pointX > rectX && pointX < rectX + rectWidth &&
                pointY > rectY && pointY < rectY + rectHeight

    private fun addMouse(touchEventX: Float, touchEventY: Float) {
        val mouseEntity = MouseEntity(
            x = playgroundSize.value.width / 2 - mouseWidthPx / 2,
            y = playgroundSize.value.height / 2 - mouseHeightPx / 2,
            width = mouseWidthPx,
            height = mouseHeightPx
        )
        mousesList.add(mouseEntity)
        sendRemoteMouseCoords(
            mouseEntity.id,
            CoordsEntity(mouseEntity.x, mouseEntity.y, mouseEntity.zIndex)
        )
        pickMouseToDrag(mouseEntity, touchEventX, touchEventY)
    }

    private fun updateMouseEntity(
        currentMouseToDrag: MouseEntity,
        touchEventX: Float,
        touchEventY: Float
    ): MouseEntity {
        val updatedMouseEntity = currentMouseToDrag.copy()
        getAngleIfExceedThreshold(
            fromX = currentMouseToDrag.x,
            fromY = currentMouseToDrag.y,
            toX = touchEventX - dragOffset.value.x,
            toY = touchEventY - dragOffset.value.y,
            threshold = mouseRotationDeltaThresholdPx
        )?.let {
            updatedMouseEntity.rotation = it.toFloat()
        }
        updatedMouseEntity.x = touchEventX - dragOffset.value.x
        updatedMouseEntity.y = touchEventY - dragOffset.value.y
        if (isPointInsideRect(
                updatedMouseEntity.x + updatedMouseEntity.width / 2,
                updatedMouseEntity.y + updatedMouseEntity.height / 2,
                mouseHoleRect.value
            )
        ) {
            updatedMouseEntity.zIndex = 3f
        } else if (!currentMouseToDrag.isEscaping) {
            updatedMouseEntity.isEscaping = true
            updatedMouseEntity.zIndex = 5f
        } else if (currentMouseToDrag.zIndex == 3f) {
            updatedMouseEntity.zIndex = 0f
        }
        return updatedMouseEntity
    }
}