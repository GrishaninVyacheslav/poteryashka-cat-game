package io.github.poteryashkagame.poteryashka_admin.details.presentation

import android.os.Bundle
import android.view.View
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.poteryashkagame.poteryashka_admin.details.databinding.FragmentDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment :
    BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    companion object {
        val DETAILS_ID_ARG = "DETAILS_ID"
        fun newInstance(detailsId: String) = DetailsFragment().apply {
            arguments = Bundle().apply { putString(DETAILS_ID_ARG, detailsId) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding){
        details.text = "DETAILS \"${requireArguments().getString(DETAILS_ID_ARG)}\""
    }

    private val viewModel: DetailsViewModel by viewModel()
}