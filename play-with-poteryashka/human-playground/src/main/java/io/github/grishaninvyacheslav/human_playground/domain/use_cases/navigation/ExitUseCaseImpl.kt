package io.github.grishaninvyacheslav.human_playground.domain.use_cases.navigation

import com.github.terrakok.cicerone.Router
import io.github.grishaninvyacheslav.navigation.domain.use_cases.ExitUseCase

class ExitUseCaseImpl(
    private val router: Router
): ExitUseCase {
    override fun invoke() {
        router.exit()
    }
}