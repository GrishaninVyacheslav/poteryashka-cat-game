package io.github.grishaninvyacheslav.human_playground.domain.use_cases.playground

import androidx.lifecycle.LiveData
import io.github.grishaninvyacheslav.human_playground.presentation.playground.PlaygroundState

interface PlaygroundUseCase {
    val playgroundState: LiveData<PlaygroundState>
    suspend fun updateState()
    suspend fun tryLaunchGame()
}