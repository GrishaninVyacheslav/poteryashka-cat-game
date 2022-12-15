package io.github.poteryashkagame.poteryashka_admin.pets_list.di

import com.github.terrakok.cicerone.Router
import com.google.firebase.firestore.FirebaseFirestore
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToPetsListUseCase
import io.github.poteryashkagame.poteryashka_admin.pets_list.domain.use_cases.GetPetsListUseCase
import io.github.poteryashkagame.poteryashka_admin.pets_list.domain.use_cases.GetPetsListUseCaseImpl
import io.github.poteryashkagame.poteryashka_admin.pets_list.domain.use_cases.NavigateToPetsListUseCaseImpl
import org.koin.dsl.module

val petsListUseCaseModule = module {
    factory { provideNavigateToPetsList(get()) }
    factory { provideGetPetsList(get()) }
}

fun provideNavigateToPetsList(
    router: Router
): NavigateToPetsListUseCase = NavigateToPetsListUseCaseImpl(router)

fun provideGetPetsList(cloudDatabase: FirebaseFirestore): GetPetsListUseCase = GetPetsListUseCaseImpl(cloudDatabase)