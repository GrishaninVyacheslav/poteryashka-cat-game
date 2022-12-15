package io.github.poteryashkagame.poteryashka_admin.pets_list.domain.use_cases

import com.google.firebase.firestore.FirebaseFirestore
import io.github.poteryashkagame.poteryashka_admin.pets_list.data.entity.PetDisplayableItemEntity
import kotlinx.coroutines.tasks.await

class GetPetsListUseCaseImpl(
    private val cloudDatabase: FirebaseFirestore,
) : GetPetsListUseCase {
    override suspend fun invoke(): List<PetDisplayableItemEntity> {
        val petList = mutableListOf<PetDisplayableItemEntity>()
        cloudDatabase
            .collection("pets").get().await().documents.forEach {
                it.data?.let { documentData ->
                    petList.add(
                        PetDisplayableItemEntity(
                            documentData["id"] as String,
                            documentData["name"] as String,
                            documentData["age"] as Long?,
                            documentData["photoUrl"] as String?,
                        )
                    )
                } ?: run {
                    throw RuntimeException("GetPetsListUseCaseImpl: pets document is null")
                }
            }
        return petList
    }
}