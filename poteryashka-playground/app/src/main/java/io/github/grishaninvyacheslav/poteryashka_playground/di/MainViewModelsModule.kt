package io.github.grishaninvyacheslav.poteryashka_playground.di

import io.github.grishaninvyacheslav.poteryashka_playground.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelsModule = module {
    viewModel { MainViewModel(get()) }
}