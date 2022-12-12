package io.github.poteryashkagame.poteryashka_admin.app.presentation

import androidx.lifecycle.ViewModel
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToListUseCase

class MainViewModel(
    private val navigateToListUseCase: NavigateToListUseCase
) : ViewModel() {

    fun navigateToHumanPlayground() = navigateToListUseCase()
}