package io.github.grishaninvyacheslav.poteryashka_playground.playground.presentation

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.cat_side.CatchAMouseCatSideFragment
import io.github.grishaninvyacheslav.poteryashka_playground.playground.R
import io.github.grishaninvyacheslav.poteryashka_playground.playground.databinding.FragmentPlaygroundBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaygroundFragment :
    BaseFragment<FragmentPlaygroundBinding>(FragmentPlaygroundBinding::inflate) {

    companion object {
        fun newInstance() = PlaygroundFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playgroundState.observe(viewLifecycleOwner) { renderPlaygroundState(it) }

    }

    private fun renderPlaygroundState(state: PlaygroundState) {
        when (state) {
            PlaygroundState.Loading -> {}
            is PlaygroundState.Active -> launchGame(state.secondsInFuture)
            PlaygroundState.Finished -> closeGame()
        }
    }

    private fun launchGame(secondsInFuture: Long) {
        if (secondsInFuture <= 0) {
            viewModel.gameTimeExpiration()
            return
        }
        object :
            CountDownTimer(secondsInFuture * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("[MYLOG_2]", "millisUntilFinished: $millisUntilFinished")
            }

            override fun onFinish() {
                viewModel.gameTimeExpiration()
                this.cancel()
            }
        }.start()
        binding.playgroundContainer.isVisible = true
        val manager = parentFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(
            R.id.playground_container,
            CatchAMouseCatSideFragment.newInstance(),
            "CATCH_A_MOUSE_GAME_FRAGMENT"
        )
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun closeGame() {
        parentFragmentManager.findFragmentByTag("CATCH_A_MOUSE_GAME_FRAGMENT")?.let {
            parentFragmentManager.beginTransaction().remove(it).commit()
        }
        binding.playgroundContainer.isVisible = false
    }

    private val viewModel: PlaygroundViewModel by viewModel()
}