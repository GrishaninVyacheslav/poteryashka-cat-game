package io.github.poteryashkagame.poteryashka_admin.list.di

import com.github.terrakok.cicerone.Router
import io.github.poteryashkagame.poteryashka_admin.list.domain.use_cases.NavigateToListUseCaseImpl
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToListUseCase
import org.koin.dsl.module

val listUseCasesModule = module {
    factory { provideNavigateToList(get()) }
}

fun provideNavigateToList(router: Router): NavigateToListUseCase =
    NavigateToListUseCaseImpl(router)
