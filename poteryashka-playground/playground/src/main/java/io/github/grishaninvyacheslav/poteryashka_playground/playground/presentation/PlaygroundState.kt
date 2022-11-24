package io.github.grishaninvyacheslav.poteryashka_playground.playground.presentation

sealed class PlaygroundState {
    object Loading: PlaygroundState()
    data class Active(val secondsInFuture: Long): PlaygroundState()
    object Finished: PlaygroundState()
}