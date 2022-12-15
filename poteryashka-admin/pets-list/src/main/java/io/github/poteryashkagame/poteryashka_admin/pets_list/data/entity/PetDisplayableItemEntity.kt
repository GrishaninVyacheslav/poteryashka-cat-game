package io.github.poteryashkagame.poteryashka_admin.pets_list.data.entity

import io.github.grishaninvyacheslav.core_ui.presentation.DisplayableItem

data class PetDisplayableItemEntity(
    val id: String,
    val name: String,
    val age: Long?,
    val photoUrl: String?,
) : DisplayableItem