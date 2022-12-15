package io.github.poteryashkagame.poteryashka_admin.pets_editor.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.ExitPetsEditorUseCase
import io.github.poteryashkagame.poteryashka_admin.pets_editor.data.entity.PetDisplayableItem
import io.github.poteryashkagame.poteryashka_admin.pets_editor.domain.PetDataUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeoutException

class PetsEditorViewModel(
    private val exitPetsEditorUseCase: ExitPetsEditorUseCase,
    private val petDataUseCase: PetDataUseCase
) : ViewModel() {
    private val mutablePetsEditorState: MutableLiveData<PetsState> = MutableLiveData()

    fun getPetsEditorState(petId: String?): LiveData<PetsState> {
        if (mutablePetsEditorState.value != null) {
            return mutablePetsEditorState
        }
        if (petId.isNullOrBlank()) {
            this.petId = UUID.randomUUID().toString()
            mutablePetsEditorState.value = PetsState.Success(
                PetDisplayableItem("")
            )
        } else {
            this.petId = petId
            fetchPetData()
        }
        return mutablePetsEditorState
    }

    fun fetchPetData() {
        mutablePetsEditorState.value = PetsState.Loading
        viewModelScope.launch(Dispatchers.IO + petsEditorExceptionHandler) {
            mutablePetsEditorState.postValue(
                PetsState.Success(petDataUseCase.get(petId).toPetDisplayableItem())
            )
        }
    }

    fun savePet(pet: PetDisplayableItem) {
        mutablePetsEditorState.value = PetsState.Loading
        viewModelScope.launch(Dispatchers.IO + petsEditorExceptionHandler) {
            petDataUseCase.save(pet.toPetEntity(petId))
            mutablePetsEditorState.postValue(
                PetsState.Saved
            )
        }
    }

    fun removePet() {
        mutablePetsEditorState.value = PetsState.Loading
        viewModelScope.launch(Dispatchers.IO + petsEditorExceptionHandler) {
            petDataUseCase.remove(petId)
            mutablePetsEditorState.postValue(
                PetsState.Removed
            )
        }
    }

    fun exit() {
        exitPetsEditorUseCase()
    }

    private var petId = "null"

    private val petsEditorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("[MYLOG]", "petsEditorExceptionHandler: throwable")
        mutablePetsEditorState.postValue(PetsState.Error(throwable))
    }
}