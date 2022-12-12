package io.github.poteryashkagame.poteryashka_admin.list.di

import io.github.poteryashkagame.poteryashka_admin.list.presentation.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listViewModelsModule = module {
    viewModel { ListViewModel(get()) }
}