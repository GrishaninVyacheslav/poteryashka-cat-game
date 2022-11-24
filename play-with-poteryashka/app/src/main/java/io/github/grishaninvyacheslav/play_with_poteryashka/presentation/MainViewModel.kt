package io.github.grishaninvyacheslav.play_with_poteryashka.presentation

import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToRoomsListUseCase

class MainViewModel(
    private val navigateToRoomsListUseCase: NavigateToRoomsListUseCase
) : ViewModel() {

    fun navigateToHumanPlayground() = navigateToRoomsListUseCase()
}