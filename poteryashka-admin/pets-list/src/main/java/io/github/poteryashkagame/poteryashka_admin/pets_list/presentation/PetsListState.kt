package io.github.poteryashkagame.poteryashka_admin.pets_list.presentation

import io.github.poteryashkagame.poteryashka_admin.pets_list.data.entity.PetDisplayableItemEntity

sealed class PetsListState {
    object Loading : PetsListState()
    data class Success(val pets: List<PetDisplayableItemEntity>) : PetsListState()
    data class Error(val error: Throwable) : PetsListState()
}