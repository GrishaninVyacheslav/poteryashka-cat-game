package io.github.poteryashkagame.poteryashka_admin.pets_list.domain.use_cases

import io.github.poteryashkagame.poteryashka_admin.pets_list.data.entity.PetDisplayableItemEntity

interface GetPetsListUseCase {
    suspend operator fun invoke(): List<PetDisplayableItemEntity>
}