package io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.presentation

import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToHumanPlaygroundUseCase

class RoomsListViewModel(
    private val navigateToHumanPlaygroundUseCase: NavigateToHumanPlaygroundUseCase,
) : ViewModel() {
    fun openRoom() = navigateToHumanPlaygroundUseCase()
}