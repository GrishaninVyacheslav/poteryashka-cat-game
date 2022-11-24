package io.github.grishaninvyacheslav.poteryashka_playground.playground.domain.use_cases.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.grishaninvyacheslav.poteryashka_playground.navigation.domain.use_cases.NavigateToPlaygroundUseCase
import io.github.grishaninvyacheslav.poteryashka_playground.playground.presentation.PlaygroundFragment

class NavigateToPlaygroundUseCaseImpl(
    private val router: Router
) : NavigateToPlaygroundUseCase {
    override fun invoke() =
        router.replaceScreen(FragmentScreen { PlaygroundFragment.newInstance() })

}