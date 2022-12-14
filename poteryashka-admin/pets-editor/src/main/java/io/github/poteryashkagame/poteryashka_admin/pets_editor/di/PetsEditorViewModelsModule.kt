package io.github.poteryashkagame.poteryashka_admin.pets_editor.di

import io.github.poteryashkagame.poteryashka_admin.pets_editor.presentation.PetsEditorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val petsEditorViewModelsModule = module {
    viewModel { PetsEditorViewModel(get(), get()) }
}