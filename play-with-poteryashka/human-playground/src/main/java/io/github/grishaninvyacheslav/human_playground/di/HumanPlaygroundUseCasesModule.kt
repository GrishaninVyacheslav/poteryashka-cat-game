package io.github.grishaninvyacheslav.human_playground.di

import com.github.terrakok.cicerone.Router
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import io.github.grishaninvyacheslav.core.data.provider.time.TimeProvider
import io.github.grishaninvyacheslav.human_playground.data.repositories.DonationRepository
import io.github.grishaninvyacheslav.human_playground.domain.use_cases.navigation.ExitUseCaseImpl
import io.github.grishaninvyacheslav.human_playground.domain.use_cases.navigation.NavigateToDonationUseCaseImpl
import io.github.grishaninvyacheslav.human_playground.domain.use_cases.navigation.NavigateToHumanPlaygroundUseCaseImpl
import io.github.grishaninvyacheslav.human_playground.domain.use_cases.playground.PlaygroundUseCase
import io.github.grishaninvyacheslav.human_playground.domain.use_cases.playground.PlaygroundUseCaseImpl
import io.github.grishaninvyacheslav.navigation.domain.use_cases.ExitUseCase
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToDonationUseCase
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToHumanPlaygroundUseCase
import org.koin.dsl.module

val humanPlaygroundUseCasesModule = module {
    factory { provideNavigateToHumanPlayground(get()) }
    factory { provideNavigateToDonation(get()) }
    factory { provideExit(get()) }
    factory { providePlayground(get(), get(), get(), get()) }
}

fun provideNavigateToHumanPlayground(router: Router): NavigateToHumanPlaygroundUseCase =
    NavigateToHumanPlaygroundUseCaseImpl(router)

fun provideNavigateToDonation(router: Router): NavigateToDonationUseCase =
    NavigateToDonationUseCaseImpl(router)

fun provideExit(router: Router): ExitUseCase =
    ExitUseCaseImpl(router)

fun providePlayground(
    realtimeDatabase: FirebaseDatabase,
    cloudDatabase: FirebaseFirestore,
    donationRepository: DonationRepository,
    timeProvider: TimeProvider
): PlaygroundUseCase =
    PlaygroundUseCaseImpl(realtimeDatabase, cloudDatabase, donationRepository, timeProvider)