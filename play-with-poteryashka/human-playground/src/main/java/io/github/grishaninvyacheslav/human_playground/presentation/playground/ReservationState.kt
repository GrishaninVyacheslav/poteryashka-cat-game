package io.github.grishaninvyacheslav.human_playground.presentation.playground

sealed class ReservationState {
    data class Reserved(val secondsLeft: Long): ReservationState()
    object NotReserved: ReservationState()
}