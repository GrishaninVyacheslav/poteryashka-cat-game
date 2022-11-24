package io.github.grishaninvyacheslav.human_playground.di

import com.google.firebase.firestore.FirebaseFirestore
import io.github.grishaninvyacheslav.human_playground.data.repositories.DonationRepository
import io.github.grishaninvyacheslav.human_playground.data.repositories.DonationRepositoryImpl
import org.koin.dsl.module

val humanPlaygroundDataModule = module {
    single { provideDonationRepository(get()) }
}

fun provideDonationRepository(cloudDatabase: FirebaseFirestore): DonationRepository =
    DonationRepositoryImpl(cloudDatabase)