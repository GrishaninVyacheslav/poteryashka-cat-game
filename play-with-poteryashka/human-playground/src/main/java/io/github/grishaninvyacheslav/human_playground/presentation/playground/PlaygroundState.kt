package io.github.grishaninvyacheslav.human_playground.presentation.playground

sealed class PlaygroundState {
    /** Состояние комнаты загружется */
    object Loading: PlaygroundState()
    /** Комната не занята */
    object Free: PlaygroundState()
    /** Другой пользователь занял комнату */
    data class Reserved(val secondsInFuture: Long): PlaygroundState()
    /** Текущий пользователь оплатил комнату, но пока она не занята */
    object Paid: PlaygroundState()
    /** Текущий пользователь занял комнату */
    data class Playing(val secondsInFuture: Long): PlaygroundState()
}