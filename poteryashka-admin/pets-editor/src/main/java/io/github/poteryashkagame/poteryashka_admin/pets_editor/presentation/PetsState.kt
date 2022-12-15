package io.github.poteryashkagame.poteryashka_admin.pets_editor.presentation

import io.github.poteryashkagame.poteryashka_admin.pets_editor.data.entity.PetDisplayableItem

sealed class PetsState {
    object Loading : PetsState()
    data class Success(val pet: PetDisplayableItem) : PetsState()
    data class Error(val error: Throwable) : PetsState()
    object Saved: PetsState()
    object Removed: PetsState()
}