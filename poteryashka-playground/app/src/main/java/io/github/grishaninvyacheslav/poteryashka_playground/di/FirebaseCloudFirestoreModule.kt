package io.github.grishaninvyacheslav.poteryashka_playground.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseCloudFirestoreModule = module {
    single { provideCloudDatabase() }
}

fun provideCloudDatabase(): FirebaseFirestore =
    Firebase.firestore