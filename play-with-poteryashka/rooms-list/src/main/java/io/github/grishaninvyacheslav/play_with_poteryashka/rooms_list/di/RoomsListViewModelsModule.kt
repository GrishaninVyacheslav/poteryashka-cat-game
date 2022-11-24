package io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.di

import io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.presentation.RoomsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomsListViewModelsModule = module {
    viewModel { RoomsListViewModel(get()) }
}