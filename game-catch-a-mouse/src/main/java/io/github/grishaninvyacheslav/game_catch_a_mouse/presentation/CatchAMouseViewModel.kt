package io.github.grishaninvyacheslav.game_catch_a_mouse.presentation

import android.view.MotionEvent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity.MouseEntity

interface CatchAMouseViewModel {
    val mousesList: SnapshotStateList<MouseEntity>
    val widthScale: MutableState<Float>
    val heightScale: MutableState<Float>
    fun setLocationOffset(offset: Offset)
    fun setMouseCollisionSize(widthPx: Float, heightPx: Float)
    fun setMouseRotationDeltaThresholdPx(thresholdPx: Float)
    fun onViewSizeChanged(size: IntSize, dpSizePx: Float)
    fun touchDown(motionEvent: MotionEvent)
    fun touchMove(motionEvent: MotionEvent)
    fun touchUp(motionEvent: MotionEvent)
}