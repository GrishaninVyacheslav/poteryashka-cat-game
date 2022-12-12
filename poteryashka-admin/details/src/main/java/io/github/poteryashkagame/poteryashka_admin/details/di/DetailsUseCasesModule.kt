package io.github.poteryashkagame.poteryashka_admin.details.di

import com.github.terrakok.cicerone.Router
import io.github.poteryashkagame.poteryashka_admin.details.domain.use_cases.NavigateToDetailsUseCaseImpl
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToDetailsUseCase
import org.koin.dsl.module

val detailsUseCasesModule = module {
    factory { provideNavigateToDetails(get()) }
}

fun provideNavigateToDetails(router: Router): NavigateToDetailsUseCase =
    NavigateToDetailsUseCaseImpl(router)
