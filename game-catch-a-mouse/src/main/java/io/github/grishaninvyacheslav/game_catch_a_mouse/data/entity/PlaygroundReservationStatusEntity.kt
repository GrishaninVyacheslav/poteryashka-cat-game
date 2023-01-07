package io.github.grishaninvyacheslav.game_catch_a_mouse.data.entity

data class PlaygroundReservationStatusEntity(
    var curr_id: String = "",
    var expiration_timestamp: Long = 0,
    var paid_ids: MutableList<String> = mutableListOf(),
    val maintaining_message: String = ""
)