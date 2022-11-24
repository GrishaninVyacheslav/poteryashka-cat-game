package io.github.grishaninvyacheslav.game_catch_a_mouse.di

import io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.cat_side.CatchAMouseCatSideViewModelImpl
import io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.human_side.CatchAMouseHumanSideViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val catchAMouseViewModelsModule = module {
    viewModel { CatchAMouseHumanSideViewModelImpl(get()) }
    viewModel { CatchAMouseCatSideViewModelImpl(get()) }
}

