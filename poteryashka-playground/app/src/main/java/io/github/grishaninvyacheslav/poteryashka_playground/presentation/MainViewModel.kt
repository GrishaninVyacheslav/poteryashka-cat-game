package io.github.grishaninvyacheslav.poteryashka_playground.presentation

import androidx.lifecycle.ViewModel
import io.github.grishaninvyacheslav.poteryashka_playground.navigation.domain.use_cases.NavigateToPlaygroundUseCase

class MainViewModel(
    private val navigateToPlaygroundUseCase: NavigateToPlaygroundUseCase
) : ViewModel() {

    fun navigateToPlayground() = navigateToPlaygroundUseCase()
}