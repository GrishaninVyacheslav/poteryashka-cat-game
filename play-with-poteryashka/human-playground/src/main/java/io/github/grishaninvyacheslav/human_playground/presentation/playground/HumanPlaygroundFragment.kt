package io.github.grishaninvyacheslav.human_playground.presentation.playground

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import io.github.grishaninvyacheslav.core_ui.presentation.BaseFragment
import io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.human_side.CatchAMouseHumanSideFragment
import io.github.grishaninvyacheslav.human_playground.R
import io.github.grishaninvyacheslav.human_playground.databinding.FragmentHumanPlaygroundBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HumanPlaygroundFragment :
    BaseFragment<FragmentHumanPlaygroundBinding>(FragmentHumanPlaygroundBinding::inflate) {

    companion object {
        fun newInstance() = HumanPlaygroundFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playgroundState.observe(viewLifecycleOwner) { renderPlaygroundState(it) }
        initViews()
    }

    private fun initViews() = with(binding) {
        webViewBlock.setOnClickListener { }
        with(cameraWebView) {
            settings.javaScriptEnabled = true
            loadUrl("https://ipeye.ru/ipeye_service/api/iframe.php?iframe_player=1&dev=ieiLmTufliX8iOvLWBULmwJ0SZKB7P&autoplay=1&archive=1")
        }
        launch.setOnClickListener {
            Log.d("[MYLOG]", "setOnClickListener")
            viewModel.launch()
        }
    }

    private fun renderPlaygroundState(state: PlaygroundState) {
        Log.d("[MYLOG]", "renderPlaygroundState: $state")
        when (state) {
            PlaygroundState.Loading -> with(binding){
                progressBar.isVisible = true
                gameHint.isVisible = false
                playgroundContainer.isVisible = false
                gameOption.isVisible = false
                launch.isVisible = false
            }
            PlaygroundState.Free -> with(binding){
                progressBar.isVisible = false
                gameHint.text = getString(R.string.game_hint_not_paid)
                gameHint.isVisible = true
                launch.text = getString(R.string.make_a_donation)
                launch.isVisible = true
                closeGame()
                playgroundContainer.isVisible = false
                gameOption.isVisible = true
            }
            is PlaygroundState.Reserved -> with(binding) {
                progressBar.isVisible = false
                initTimer(state.secondsInFuture, getString(R.string.game_hint_timer_when_reserved_format_string))
                launch.text = getString(R.string.refresh)
                closeGame()
                playgroundContainer.isVisible = false
                gameOption.isVisible = true
                launch.isVisible = true
            }
            PlaygroundState.Paid -> with(binding) {
                progressBar.isVisible = false
                gameHint.text = getString(R.string.game_hint_paid)
                gameHint.isVisible = true
                playgroundContainer.isVisible = false
                gameOption.isVisible = true
                launch.text = getString(R.string.launch_game)
                launch.isVisible = true
            }
            is PlaygroundState.Playing -> {
                binding.progressBar.isVisible = false
                initTimer(state.secondsInFuture, getString(R.string.game_hint_timer_when_playing_format_string))
                launchGame()
            }
            is PlaygroundState.Maintaining -> with(binding){
                progressBar.isVisible = false
                gameHint.text = state.message
                gameHint.isVisible = true
                playgroundContainer.isVisible = false
                gameOption.isVisible = false
                launch.text = getString(R.string.refresh)
                launch.isVisible = true
            }
        }
    }

    private fun initTimer(secondsInFuture: Long, gameHintTimerFormatString: String) {
        Log.d("[MYLOG]", "initTimer secondsInFuture: $secondsInFuture")
        binding.gameHint.text = getString(R.string.game_hint_standby)
        binding.gameHint.isVisible = true
        if(secondsInFuture < 0){
            viewModel.timestampExpiration()
        }
        object : CountDownTimer(secondsInFuture * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                Log.d("[MYLOG]", "secondsLeft: $secondsLeft")
                binding.gameHint.text = String.format(
                    gameHintTimerFormatString,
                    if(secondsLeft / 60 < 10) "0${secondsLeft / 60}" else "${secondsLeft / 60}",
                    if(secondsLeft % 60 < 10) "0${secondsLeft % 60}" else "${secondsLeft % 60}"
                )
            }

            override fun onFinish() {
                viewModel.timestampExpiration()
                this.cancel()
            }
        }.start()
    }

    private fun launchGame() {
        binding.playgroundContainer.isVisible = true
        binding.gameOption.isVisible = false
        binding.launch.isVisible = false
        val manager = parentFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(
            R.id.playground_container,
            CatchAMouseHumanSideFragment.newInstance(),
            "CATCH_A_MOUSE_GAME_FRAGMENT"
        )
        transaction.commit()
    }

    private fun closeGame() {
        parentFragmentManager.findFragmentByTag("CATCH_A_MOUSE_GAME_FRAGMENT")?.let {
            parentFragmentManager.beginTransaction().remove(it).commit()
        }
    }

    private val viewModel: PlaygroundViewModel by viewModel()
}