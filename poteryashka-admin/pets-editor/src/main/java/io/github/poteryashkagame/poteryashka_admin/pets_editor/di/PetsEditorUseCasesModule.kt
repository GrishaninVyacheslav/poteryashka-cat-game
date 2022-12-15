package io.github.poteryashkagame.poteryashka_admin.pets_editor.di

import com.github.terrakok.cicerone.Router
import com.google.firebase.firestore.FirebaseFirestore
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.ExitPetsEditorUseCase
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToPetsEditorUseCase
import io.github.poteryashkagame.poteryashka_admin.pets_editor.domain.ExitPetsEditorUseCaseImpl
import io.github.poteryashkagame.poteryashka_admin.pets_editor.domain.PetDataUseCase
import io.github.poteryashkagame.poteryashka_admin.pets_editor.domain.PetDataUseCaseImpl
import io.github.poteryashkagame.poteryashka_admin.pets_editor.domain.NavigateToPetsEditorUseCaseImpl
import org.koin.dsl.module

val petsEditorUseCasesModule = module {
    factory { provideNavigateToPetsEditor(get()) }
    factory { provideExitPetsEditor(get()) }
    factory { provideGetPetData(get()) }
}

fun provideNavigateToPetsEditor(router: Router): NavigateToPetsEditorUseCase =
    NavigateToPetsEditorUseCaseImpl(router)

fun provideExitPetsEditor(router: Router): ExitPetsEditorUseCase =
    ExitPetsEditorUseCaseImpl(router)

fun provideGetPetData(cloudDatabase: FirebaseFirestore): PetDataUseCase =
    PetDataUseCaseImpl(cloudDatabase)
