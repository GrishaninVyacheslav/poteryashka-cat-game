package io.github.grishaninvyacheslav.play_with_poteryashka.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseCloudFirestoreModule = module {
    single { provideCloudDatabase() }
}

fun provideCloudDatabase(): FirebaseFirestore =
    Firebase.firestore