package io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.cat_side

import android.util.Log
import android.view.MotionEvent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.PlaygroundEntity
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.MouseEntity
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.SizeEntity
import io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.CatchAMouseViewModel
import kotlin.math.abs
import kotlin.math.atan2

class CatchAMouseCatSideViewModelImpl(
    private val database: FirebaseDatabase
) : ViewModel(), CatchAMouseViewModel {
    init {

        database.getReference("playground_1").child("catch_a_mouse")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue<PlaygroundEntity>()
                    value?.mouses_list?.forEach { entry ->
                        val mouseEntityIndex = mousesList.indexOfFirst { it.id == entry.key }
                        val mouseEntityLocalX = entry.value.x * dpSizePx * widthScale.value
                        val mouseEntityLocalY = entry.value.y * dpSizePx * heightScale.value
                        if (mouseEntityIndex == -1) {
                            addMouse(entry.key, mouseEntityLocalX, mouseEntityLocalY)
                        } else {
                            mousesList[mouseEntityIndex] = updateMouseEntity(
                                mousesList[mouseEntityIndex],
                                mouseEntityLocalX,
                                mouseEntityLocalY,
                                entry.value.zIndex
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("[MYLOG]", "Failed to read value.", error.toException())
                }
            })
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
        widthScale.value = (size.width / dpSizePx) / fullSizeWidth
        heightScale.value = (size.height / dpSizePx) / fullSizeHeight
        playgroundSize.value = size
        fullSizeWidth = size.width / dpSizePx
        fullSizeHeight = size.height / dpSizePx
        database.getReference("playground_1").child("catch_a_mouse").child("source_screen_size")
            .setValue(
                SizeEntity(fullSizeWidth, fullSizeHeight)
            )
        mouseHoleRect.value = Rect(
            Offset((size.width / 2).toFloat(), (size.height / 2).toFloat()), mouseWidthPx / 2F
        )
    }

    override fun touchDown(motionEvent: MotionEvent) {
        mousesList.forEach { mouseEntity ->
            if (isPointInsideRect(
                    pointX = motionEvent.x,
                    pointY = motionEvent.y,
                    rectX = mouseEntity.x,
                    rectY = mouseEntity.y,
                    rectWidth = mouseEntity.width,
                    rectHeight = mouseEntity.height
                )
            ) {
                mousesList.remove(mouseEntity)
                removeRemoteMouse(mouseEntity.id)
                return
            }
        }
    }

    override fun touchMove(motionEvent: MotionEvent) {}

    override fun touchUp(motionEvent: MotionEvent) {}

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

    private fun addMouse(id: String, touchEventX: Float, touchEventY: Float) {
        val mouseEntity = MouseEntity(
            id = id,
            x = touchEventX,
            y = touchEventY,
            width = mouseWidthPx,
            height = mouseHeightPx
        )
        mousesList.add(mouseEntity)
    }

    private fun removeRemoteMouse(mouseId: String) {
        val myRef =
            database.getReference("playground_1").child("catch_a_mouse").child("mouses_list")
                .child(mouseId)
        myRef.removeValue()
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

    private fun updateMouseEntity(
        mouseEntity: MouseEntity,
        touchEventX: Float,
        touchEventY: Float,
        zIndex: Float
    ): MouseEntity {
        val updatedMouseEntity = mouseEntity.copy()
        getAngleIfExceedThreshold(
            fromX = mouseEntity.x,
            fromY = mouseEntity.y,
            toX = touchEventX - dragOffset.value.x,
            toY = touchEventY - dragOffset.value.y,
            threshold = mouseRotationDeltaThresholdPx
        )?.let {
            updatedMouseEntity.rotation = it.toFloat()
        }
        updatedMouseEntity.x = touchEventX - dragOffset.value.x
        updatedMouseEntity.y = touchEventY - dragOffset.value.y
        updatedMouseEntity.zIndex = zIndex
        return updatedMouseEntity
    }
}