package io.github.poteryashkagame.poteryashka_admin.pets_list.di

import io.github.poteryashkagame.poteryashka_admin.pets_list.presentation.PetsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val petsListViewModelsModule = module {
    viewModel { PetsListViewModel(get(), get()) }
}