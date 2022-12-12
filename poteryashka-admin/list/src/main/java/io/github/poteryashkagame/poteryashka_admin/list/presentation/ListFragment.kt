package io.github.poteryashkagame.poteryashka_admin.list.presentation

import android.os.Bundle
import android.view.View
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.poteryashkagame.poteryashka_admin.list.databinding.FragmentListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment :
    BaseFragment<FragmentListBinding>(FragmentListBinding::inflate) {

    companion object {
        fun newInstance() = ListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding){
        detailsA.setOnClickListener {
            viewModel.openDetails("A")
        }
        detailsB.setOnClickListener {
            viewModel.openDetails("B")
        }
        detailsC.setOnClickListener {
            viewModel.openDetails("C")
        }
        detailsD.setOnClickListener {
            viewModel.openDetails("D")
        }
    }

    private val viewModel: ListViewModel by viewModel()
}