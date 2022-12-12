package io.github.poteryashkagame.poteryashka_admin.list.presentation

import androidx.lifecycle.ViewModel
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToDetailsUseCase

class ListViewModel(
    private val navigateToDetailsUseCase: NavigateToDetailsUseCase,
) : ViewModel() {
    fun openDetails(detailsId: String) = navigateToDetailsUseCase(detailsId)
}