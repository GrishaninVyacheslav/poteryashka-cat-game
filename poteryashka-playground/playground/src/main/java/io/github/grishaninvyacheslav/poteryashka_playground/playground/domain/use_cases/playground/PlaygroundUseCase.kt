package io.github.grishaninvyacheslav.poteryashka_playground.playground.domain.use_cases.playground

import androidx.lifecycle.LiveData
import io.github.grishaninvyacheslav.poteryashka_playground.playground.presentation.PlaygroundState

interface PlaygroundUseCase {
    val playgroundState: LiveData<PlaygroundState>
    suspend fun finishGame()
}