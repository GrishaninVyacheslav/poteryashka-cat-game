package io.github.poteryashkagame.poteryashka_admin.pets_editor.data.entity

data class PetDisplayableItem(
    val name: String,
    val breed: String? = null,
    val age: Long? = null,
    val photoUrl: String? = null,
    val description: String? = null
) {
    fun toPetEntity(petId: String): PetEntity = PetEntity(
        petId,
        name,
        breed,
        age,
        photoUrl,
        description
    )
}