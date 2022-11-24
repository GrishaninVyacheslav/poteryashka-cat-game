package io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity

import java.util.*

data class MouseEntity(
    val id: String = UUID.randomUUID().toString(),
    var isEscaping: Boolean = false,
    val width: Float = 0F,
    val height: Float = 0F,
    var x: Float = 0F,
    var y: Float = 0F,
    var rotation: Float = 0F,
    var zIndex: Float = 5F
)