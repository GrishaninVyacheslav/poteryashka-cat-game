package io.github.grishaninvyacheslav.human_playground.presentation.playground

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.grishaninvyacheslav.human_playground.domain.use_cases.playground.PlaygroundUseCase
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToDonationUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaygroundViewModel(
    private val navigateToDonationUseCase: NavigateToDonationUseCase,
    private val playgroundUseCase: PlaygroundUseCase,
) : ViewModel() {
    private val mutablePlaygroundState: MutableLiveData<PlaygroundState> = MutableLiveData()
    val playgroundState: LiveData<PlaygroundState>
        get() {
            mutablePlaygroundState.value = PlaygroundState.Loading
            Log.d("[MYLOG]", "playgroundState get()")
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                playgroundUseCase.updateState()
            }
            return mutablePlaygroundState
        }

    init {
        playgroundUseCase.playgroundState.observeForever { playgroundState ->
            mutablePlaygroundState.value = playgroundState
        }
    }

    fun launch() {
        Log.d("[MYLOG]", "launch playgroundState: ${mutablePlaygroundState.value}")
        when (mutablePlaygroundState.value) {
            PlaygroundState.Free -> {
                Log.d("[MYLOG]", "launch navigateToDonationUseCase")
                mutablePlaygroundState.value = PlaygroundState.Loading
                navigateToDonationUseCase()
            }
            is PlaygroundState.Paid, is PlaygroundState.Playing -> {
                mutablePlaygroundState.value = PlaygroundState.Loading
                viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                    playgroundUseCase.tryLaunchGame()
                }
            }
            is PlaygroundState.Reserved, is PlaygroundState.Maintaining -> {
                mutablePlaygroundState.value = PlaygroundState.Loading
                viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                    playgroundUseCase.updateState()
                }
            }
            PlaygroundState.Loading, null -> {

            }
        }
    }

    fun timestampExpiration() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        playgroundUseCase.updateState()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("[MYLOG]", "exceptionHandler: $throwable")
    }
}