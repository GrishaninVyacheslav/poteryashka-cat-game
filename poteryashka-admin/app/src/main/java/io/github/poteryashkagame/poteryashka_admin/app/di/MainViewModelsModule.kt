package io.github.poteryashkagame.poteryashka_admin.app.di

import io.github.poteryashkagame.poteryashka_admin.app.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelsModule = module {
    viewModel { MainViewModel(get()) }
}