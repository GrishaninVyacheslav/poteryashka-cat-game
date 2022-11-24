package io.github.grishaninvyacheslav.poteryashka_playground.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseRealtimeDatabaseModule = module {
    single { provideDatabase() }
}

fun provideDatabase(): FirebaseDatabase =
    Firebase.database("https://cat-game-proto-default-rtdb.europe-west1.firebasedatabase.app/")