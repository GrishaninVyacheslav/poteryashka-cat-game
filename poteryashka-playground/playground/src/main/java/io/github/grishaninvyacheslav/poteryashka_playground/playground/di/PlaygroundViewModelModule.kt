package io.github.grishaninvyacheslav.poteryashka_playground.playground.di

import io.github.grishaninvyacheslav.poteryashka_playground.playground.presentation.PlaygroundViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playgroundViewModelModule = module {
    viewModel { PlaygroundViewModel(get(), get(), get()) }
}
