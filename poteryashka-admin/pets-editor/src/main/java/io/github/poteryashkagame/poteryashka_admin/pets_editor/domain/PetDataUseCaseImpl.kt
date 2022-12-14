package io.github.poteryashkagame.poteryashka_admin.pets_editor.domain

import com.google.firebase.firestore.FirebaseFirestore
import io.github.poteryashkagame.poteryashka_admin.pets_editor.data.entity.PetEntity
import kotlinx.coroutines.tasks.await

class PetDataUseCaseImpl(
    private val cloudDatabase: FirebaseFirestore,
) : PetDataUseCase {
    override suspend fun get(petId: String): PetEntity {
        cloudDatabase
            .collection("pets")
            .document(petId).get().await().let { document ->
                document.data?.let {
                    return PetEntity(
                        it["id"] as String,
                        it["name"] as String,
                        it["breed"] as String?,
                        it["age"] as Long?,
                        it["photoUrl"] as String?,
                        it["description"] as String?
                    )
                } ?: run {
                    throw RuntimeException("PetDataUseCaseImpl get: document with petId $petId is null")
                }
            }
    }

    override suspend fun save(pet: PetEntity) {
        cloudDatabase
            .collection("pets")
            .document(pet.id).set(pet).await()
    }

    override suspend fun remove(petId: String) {
        cloudDatabase
            .collection("pets")
            .document(petId).delete().await()
    }
}