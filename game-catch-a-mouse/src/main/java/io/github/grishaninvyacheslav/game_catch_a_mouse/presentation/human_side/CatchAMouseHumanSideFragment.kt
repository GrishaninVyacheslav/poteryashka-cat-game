package io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.human_side

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.isVisible
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.grishaninvyacheslav.game_catch_a_mouse.databinding.FragmentCatchAMouseBinding
import io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.Playground
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatchAMouseHumanSideFragment :
    BaseFragment<FragmentCatchAMouseBinding>(FragmentCatchAMouseBinding::inflate) {

    companion object {
        fun newInstance() = CatchAMouseHumanSideFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playgroundState.observe(viewLifecycleOwner) { renderPlaygroundState(it) }
        binding.mapComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val playgroundLocationOnScreen = IntArray(2)
                binding.mapComposeView.getLocationOnScreen(playgroundLocationOnScreen)
                Playground(
                    viewModel,
                    Offset(
                        playgroundLocationOnScreen[0].toFloat(),
                        playgroundLocationOnScreen[1].toFloat()
                    )
                )
            }
        }
    }

    private fun renderPlaygroundState(state: PlaygroundState) {
        Log.d("[PLAYLOG]", "renderPlaygroundState $state")
        when (state) {
            PlaygroundState.Loading -> {
                setProgressBar(true)
            }
            PlaygroundState.Success -> with(binding) {
                setProgressBar(false)
                mapComposeView.apply {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    setContent {
                        val playgroundLocationOnScreen = IntArray(2)
                        mapComposeView.getLocationOnScreen(playgroundLocationOnScreen)
                        Playground(
                            viewModel,
                            Offset(
                                playgroundLocationOnScreen[0].toFloat(),
                                playgroundLocationOnScreen[1].toFloat()
                            )
                        )
                    }
                }
            }
            is PlaygroundState.Error -> Toast.makeText(
                requireContext(),
                state.error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setProgressBar(isInProgress: Boolean) = with(binding) {
        progressBar.isVisible = isInProgress
        mapComposeView.isVisible = !isInProgress
    }

    private val viewModel: CatchAMouseHumanSideViewModelImpl by viewModel()
}