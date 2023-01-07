package io.github.grishaninvyacheslav.human_playground.domain.use_cases.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.grishaninvyacheslav.human_playground.presentation.playground.HumanPlaygroundFragment
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToHumanPlaygroundUseCase

class NavigateToHumanPlaygroundUseCaseImpl(
    private val router: Router
) : NavigateToHumanPlaygroundUseCase {
    override fun invoke() =
        router.navigateTo(FragmentScreen { HumanPlaygroundFragment.newInstance() })

}