package io.github.poteryashkagame.poteryashka_admin.pets_editor.domain

import io.github.poteryashkagame.poteryashka_admin.pets_editor.data.entity.PetEntity

interface PetDataUseCase {
    suspend fun get(petId: String): PetEntity
    suspend fun save(pet: PetEntity)
    suspend fun remove(petId: String)
}