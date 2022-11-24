package io.github.grishaninvyacheslav.poteryashka_playground.playground.di

import com.github.terrakok.cicerone.Router
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import io.github.grishaninvyacheslav.poteryashka_playground.navigation.domain.use_cases.ExitUseCase
import io.github.grishaninvyacheslav.poteryashka_playground.navigation.domain.use_cases.NavigateToPlaygroundUseCase
import io.github.grishaninvyacheslav.poteryashka_playground.playground.domain.use_cases.navigation.ExitUseCaseImpl
import io.github.grishaninvyacheslav.poteryashka_playground.playground.domain.use_cases.navigation.NavigateToPlaygroundUseCaseImpl
import io.github.grishaninvyacheslav.poteryashka_playground.playground.domain.use_cases.playground.PlaygroundUseCase
import io.github.grishaninvyacheslav.poteryashka_playground.playground.domain.use_cases.playground.PlaygroundUseCaseImpl
import org.koin.dsl.module

val playgroundUseCasesModule = module {
    factory { provideNavigateToPlayground(get()) }
    factory { provideExit(get()) }
    factory { providePlayground(get(), get()) }
}

fun provideNavigateToPlayground(router: Router): NavigateToPlaygroundUseCase =
    NavigateToPlaygroundUseCaseImpl(router)

fun provideExit(router: Router): ExitUseCase = ExitUseCaseImpl(router)

fun providePlayground(
    cloudDatabase: FirebaseFirestore,
    realtimeDatabase: FirebaseDatabase
): PlaygroundUseCase = PlaygroundUseCaseImpl(cloudDatabase, realtimeDatabase)