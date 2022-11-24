package io.github.grishaninvyacheslav.game_catch_a_mouse.presentation

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import io.github.grishaninvyacheslav.game_catch_a_mouse.R
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Playground(viewModel: CatchAMouseViewModel, locationOnScreen: Offset) {
    viewModel.setLocationOffset(locationOnScreen)
    viewModel.setMouseRotationDeltaThresholdPx(
        LocalDensity.current.run { 0.dp.toPx() }
    )
    val dpSizePx = LocalDensity.current.run { 1.dp.toPx() }

//    val vector = ImageVector.vectorResource(id = R.drawable.ic_mouse)
//    val painter = rememberVectorPainter(image = vector)

    val image = ImageBitmap.imageResource(R.drawable.texture_ground)
    val backgroundBrush =
        remember(image) { ShaderBrush(ImageShader(image, TileMode.Repeated, TileMode.Repeated)) }

    Box(modifier = Modifier
        .fillMaxSize()
        .onSizeChanged {
            viewModel.onViewSizeChanged(it, dpSizePx)
        }
        .pointerInteropFilter { motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewModel.touchDown(motionEvent)
                }
                MotionEvent.ACTION_MOVE -> viewModel.touchMove(motionEvent)
                MotionEvent.ACTION_UP -> viewModel.touchUp(motionEvent)
                else -> false
            }
            true
        }) {
        viewModel.setMouseCollisionSize(
            LocalDensity.current.run { (100 / viewModel.widthScale.value).dp.toPx() },
            LocalDensity.current.run { (100 / viewModel.heightScale.value).dp.toPx() }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .zIndex(1f)
        )

        Box(
            modifier = Modifier
                .width(300.dp / viewModel.widthScale.value)
                .height(300.dp / viewModel.heightScale.value)
                .align(Alignment.Center)
                .background(Color.Black)
                .zIndex(2f)
        )

        Image(
            painter = painterResource(R.drawable.ic_mouse_hole),
            contentDescription = "MOUSE_HOLE",
            modifier = Modifier
                .width(300.dp / viewModel.widthScale.value)
                .height(300.dp / viewModel.heightScale.value)
                .align(Alignment.Center)
                .zIndex(4f)
        )

        viewModel.mousesList.forEach { mouseEntity ->
            Image(
                painter = painterResource(R.drawable.ic_mouse),
                contentDescription = "MOUSE_${mouseEntity.id}",
                modifier = Modifier
                    .width(100.dp / viewModel.widthScale.value)
                    .height(100.dp / viewModel.heightScale.value)
                    .graphicsLayer(
                        translationX = mouseEntity.x,
                        translationY = mouseEntity.y,
                        rotationZ = mouseEntity.rotation
                    )
                    .zIndex(mouseEntity.zIndex)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    Playground()
//}
