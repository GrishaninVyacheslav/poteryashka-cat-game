package io.github.poteryashkagame.poteryashka_admin.pets_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToPetsEditorUseCase
import io.github.poteryashkagame.poteryashka_admin.pets_list.domain.use_cases.GetPetsListUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetsListViewModel(
    private val navigateToPetsEditorUseCase: NavigateToPetsEditorUseCase,
    private val getPetsListUseCase: GetPetsListUseCase
) : ViewModel() {
    private val mutablePetsListState: MutableLiveData<PetsListState> = MutableLiveData()
    val petsListState: LiveData<PetsListState>
        get() {
            if (mutablePetsListState.value != null) {
                return mutablePetsListState
            }
            updatePetsListState()
            return mutablePetsListState
        }

    fun updatePetsListState() {
        mutablePetsListState.value = PetsListState.Loading
        viewModelScope.launch(Dispatchers.IO + petsListExceptionHandler) {
            mutablePetsListState.postValue(
                PetsListState.Success(getPetsListUseCase())
            )
        }
    }

    fun openPet(petId: String) {
        navigateToPetsEditorUseCase(petId)
    }

    fun addPet(){
        navigateToPetsEditorUseCase()
    }

    private val petsListExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        mutablePetsListState.postValue(PetsListState.Error(throwable))
    }
}