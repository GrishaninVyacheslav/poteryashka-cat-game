package io.github.grishaninvyacheslav.human_playground.di

import io.github.grishaninvyacheslav.human_playground.presentation.donation.DonationViewModel
import io.github.grishaninvyacheslav.human_playground.presentation.playground.PlaygroundViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val humanPlaygroundViewModelsModule = module {
    viewModel { PlaygroundViewModel(get(), get()) }
    viewModel { DonationViewModel(get(), get()) }
}