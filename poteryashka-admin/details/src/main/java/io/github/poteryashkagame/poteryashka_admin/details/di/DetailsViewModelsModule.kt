package io.github.poteryashkagame.poteryashka_admin.details.di

import io.github.poteryashkagame.poteryashka_admin.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsViewModelsModule = module {
    viewModel { DetailsViewModel() }
}