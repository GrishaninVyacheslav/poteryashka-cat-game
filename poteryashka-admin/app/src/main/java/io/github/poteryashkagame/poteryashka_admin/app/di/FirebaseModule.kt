package io.github.poteryashkagame.poteryashka_admin.app.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseModule = module {
    single { provideFirestoreDatabase() }
}

fun provideFirestoreDatabase(): FirebaseFirestore =
    Firebase.firestore.apply {
        val settings = firestoreSettings {
            isPersistenceEnabled = false
        }
        firestoreSettings = settings
    }