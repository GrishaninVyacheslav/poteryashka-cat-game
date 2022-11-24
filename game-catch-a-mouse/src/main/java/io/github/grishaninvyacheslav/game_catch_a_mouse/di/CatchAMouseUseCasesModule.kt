package io.github.grishaninvyacheslav.game_catch_a_mouse.di

import com.github.terrakok.cicerone.Router
import io.github.grishaninvyacheslav.game_catch_a_mouse.domain.use_cases.NavigateToCatchAMouseUseCaseImpl
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToCatchAMouseUseCase
import org.koin.dsl.module

val catchAMouseUseCasesModule = module {
    factory { provideNavigateToGameCatchAMouse(get()) }
}

fun provideNavigateToGameCatchAMouse(router: Router): NavigateToCatchAMouseUseCase =
    NavigateToCatchAMouseUseCaseImpl(router)