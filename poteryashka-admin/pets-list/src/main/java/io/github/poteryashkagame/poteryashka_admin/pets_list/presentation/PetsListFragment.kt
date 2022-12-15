package io.github.poteryashkagame.poteryashka_admin.pets_list.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.grishaninvyacheslav.core_ui.presentation.BottomNavigation
import io.github.poteryashkagame.poteryashka_admin.pets_list.data.entity.PetDisplayableItemEntity
import io.github.poteryashkagame.poteryashka_admin.pets_list.databinding.FragmentPetsListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

class PetsListFragment :
    BaseFragment<FragmentPetsListBinding>(FragmentPetsListBinding::inflate) {

    companion object {
        fun newInstance() = PetsListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initView()
        (requireActivity() as BottomNavigation).isBottomNavigationVisible = true
    }

    override fun onResume() {
        super.onResume()
        viewModel.updatePetsListState()
    }

    private fun initObservers() {
        viewModel.petsListState.observe(viewLifecycleOwner) { renderPetsListState(it) }
    }

    private fun initView() = with(binding) {
        retryAction.setOnClickListener {
            viewModel.updatePetsListState()
        }
        addPet.setOnClickListener {
            viewModel.addPet()
        }
    }

    private fun renderPetsListState(state: PetsListState) = when (state) {
        PetsListState.Loading -> with(binding) {
            setProgressBarStatus(true)
            setRetryError(null)
        }
        is PetsListState.Success -> with(binding) {
            setProgressBarStatus(false)
            setRetryError(null)
            initList(state.pets)
        }
        is PetsListState.Error -> with(binding) {
            setProgressBarStatus(false)
            setRetryError(state.error.message)
            showErrorMessage(state.error)
        }
    }

    private fun initList(pets: List<PetDisplayableItemEntity>) = with(binding) {
        retry.isVisible = false
        progressBar.isVisible = false
        petsList.isVisible = true
        addPet.isVisible = true
        petsList.layoutManager =
            LinearLayoutManager(requireContext())
        petsList.adapter = petsAdapter.apply {
            items = pets
        }
        petsAdapter.adapterDelegate.onClickEvent = { viewModel.openPet(it.id) }
    }

    private fun setRetryError(errorMessage: String?) = with(binding) {
        errorMessage?.let {
            petsList.isVisible = false
            addPet.isVisible = false
            progressBar.isVisible = false
            retry.isVisible = true
            retryErrorMessage.text = it
        } ?: run {
            retry.isVisible = false
            retryErrorMessage.text = ""
        }
    }

    private fun setProgressBarStatus(isInProgress: Boolean) = with(binding) {
        if(isInProgress){
            petsList.isVisible = false
            addPet.isVisible = false
            retry.isVisible = false
        }
        progressBar.isVisible = isInProgress
    }

    private val petsAdapter: PetsAdapter by inject()

    private val viewModel: PetsListViewModel by viewModel()
}