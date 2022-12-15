package io.github.poteryashkagame.poteryashka_admin.pets_list.di

import io.github.poteryashkagame.poteryashka_admin.pets_list.presentation.PetsAdapter
import io.github.poteryashkagame.poteryashka_admin.pets_list.presentation.PetsAdapterDelegate
import org.koin.dsl.module

val petsListAdaptersModule = module {
    single { providePetsAdapter() }
}

fun providePetsAdapter(): PetsAdapter =
    PetsAdapter(
        PetsAdapterDelegate()
    )
