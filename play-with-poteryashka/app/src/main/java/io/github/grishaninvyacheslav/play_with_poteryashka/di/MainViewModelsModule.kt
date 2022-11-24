package io.github.grishaninvyacheslav.play_with_poteryashka.di

import io.github.grishaninvyacheslav.play_with_poteryashka.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelsModule = module {
    viewModel { MainViewModel(get()) }
}