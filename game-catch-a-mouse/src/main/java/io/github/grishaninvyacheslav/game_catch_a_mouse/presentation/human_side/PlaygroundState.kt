package io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.human_side

sealed class PlaygroundState {
    object Loading: PlaygroundState()
    object Success: PlaygroundState()
    data class Error(val error: Throwable): PlaygroundState()
}