package io.github.grishaninvyacheslav.play_with_poteryashka.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseRealtimeDatabaseModule = module {
    single { provideRealtimeDatabase() }
}

fun provideRealtimeDatabase(): FirebaseDatabase =
    Firebase.database("https://cat-game-proto-default-rtdb.europe-west1.firebasedatabase.app/")