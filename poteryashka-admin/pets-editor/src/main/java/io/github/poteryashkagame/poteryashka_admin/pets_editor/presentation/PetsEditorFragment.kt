package io.github.poteryashkagame.poteryashka_admin.pets_editor.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.grishaninvyacheslav.core_ui.presentation.BottomNavigation
import io.github.poteryashkagame.poteryashka_admin.pets_editor.data.entity.PetDisplayableItem
import io.github.poteryashkagame.poteryashka_admin.pets_editor.databinding.FragmentPetsEditorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PetsEditorFragment :
    BaseFragment<FragmentPetsEditorBinding>(FragmentPetsEditorBinding::inflate) {

    companion object {
        val PET_ID_ARG = "PET_ID"
        fun newInstance(petId: String?) = if (!petId.isNullOrBlank()) {
            PetsEditorFragment().apply {
                arguments = Bundle().apply { putString(PET_ID_ARG, petId) }
            }
        } else {
            newInstance()
        }

        fun newInstance() = PetsEditorFragment().apply {
            arguments = Bundle().apply { putString(PET_ID_ARG, "") }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        binding.retryAction.setOnClickListener {
            viewModel.fetchPetData()
        }
        (requireActivity() as BottomNavigation).isBottomNavigationVisible = false
    }

    private fun initObservers() {
        viewModel.getPetsEditorState(requireArguments().getString(PET_ID_ARG))
            .observe(viewLifecycleOwner) { renderPetEditorState(it) }
    }

    private fun renderPetEditorState(state: PetsState){
        Log.d("[MYLOG]", "renderPetEditorState: $state")
        when (state) {

            PetsState.Loading -> with(binding) {
                setProgressBarStatus(true)
                setRetryError(null)
            }
            is PetsState.Success -> with(binding) {
                setProgressBarStatus(false)
                setRetryError(null)
                initViews(state.pet)
            }
            is PetsState.Error -> with(binding) {
                setProgressBarStatus(false)
                setRetryError(state.error.message)
                showErrorMessage(state.error)
            }
            PetsState.Removed -> {
                showToast("Pet is removed")
                viewModel.exit()
            }
            PetsState.Saved -> {
                showToast("Pet is saved")
                viewModel.exit()
            }
        }
    }

    private fun setProgressBarStatus(isInProgress: Boolean) = with(binding) {
        if(isInProgress){
            petsEditor.isVisible = false
            retry.isVisible = false
        }
        progressBar.isVisible = isInProgress
    }

    private fun setRetryError(errorMessage: String?) = with(binding) {
        errorMessage?.let {
            petsEditor.isVisible = false
            progressBar.isVisible = false
            retry.isVisible = true
            retryErrorMessage.text = it
        } ?: run {
            retry.isVisible = false
            retryErrorMessage.text = ""
        }
    }

    private fun initViews(petDisplayableItemEntity: PetDisplayableItem) = with(binding) {
        retry.isVisible = false
        progressBar.isVisible = false
        petsEditor.isVisible = true
        remove.setOnClickListener { viewModel.removePet() }
        save.setOnClickListener {
            viewModel.savePet(
                PetDisplayableItem(
                    nameInputEditText.text.toString(),
                    if(!breedInputEditText.text.isNullOrBlank()){ breedInputEditText.text.toString() } else null,
                    if(!ageInputEditText.text.isNullOrBlank()){ ageInputEditText.text.toString().toLong() } else null,
                    "https://picsum.photos/200/300",
                    if(!descriptionInputEditText.text.isNullOrBlank()){ descriptionInputEditText.text.toString() } else null,
                )
            )
        }
        with(petDisplayableItemEntity) {
            if (name.isNotBlank()) {
                nameInputEditText.setText(name)
            }
            if (!breed.isNullOrBlank()) {
                breedInputEditText.setText(breed)
            }
            if (age != null) {
                ageInputEditText.setText(age.toString())
            }
            if (!description.isNullOrBlank()) {
                descriptionInputEditText.setText(description.toString())
            }
        }
    }

    private val viewModel: PetsEditorViewModel by viewModel()
}