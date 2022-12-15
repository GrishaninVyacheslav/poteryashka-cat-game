package io.github.poteryashkagame.poteryashka_admin.app.presentation

import androidx.lifecycle.ViewModel
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToListUseCase
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToPetsListUseCase

class MainViewModel(
    private val navigateToListUseCase: NavigateToListUseCase,
    private val navigateToPetsListUseCase: NavigateToPetsListUseCase
) : ViewModel() {
    fun navigateToRoomsList() = navigateToListUseCase()
    fun navigateToPetsList() = navigateToPetsListUseCase()
}