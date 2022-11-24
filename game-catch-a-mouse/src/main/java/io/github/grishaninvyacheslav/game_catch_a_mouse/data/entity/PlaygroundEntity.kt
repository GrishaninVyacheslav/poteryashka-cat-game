package io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity

data class PlaygroundEntity(
    val source_screen_size: SizeEntity? = null,
    val mouses_list: Map<String, CoordsEntity>? = null,
    val expiration_timestamp: Long? = null
)

