package io.github.poteryashkagame.poteryashka_admin.pets_editor.data.entity

data class PetEntity(
    val id: String,
    val name: String,
    val breed: String? = null,
    val age: Long? = null,
    val photoUrl: String? = null,
    val description: String? = null
) {
    fun toPetDisplayableItem(): PetDisplayableItem = PetDisplayableItem(
        name,
        breed,
        age,
        photoUrl,
        description
    )
}