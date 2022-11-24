package io.github.grishaninvyacheslav.game_catch_a_mouse.domain.use_cases

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.grishaninvyacheslav.game_catch_a_mouse.presentation.human_side.CatchAMouseHumanSideFragment
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToCatchAMouseUseCase

class NavigateToCatchAMouseUseCaseImpl(
    private val router: Router
) : NavigateToCatchAMouseUseCase {
    override fun invoke() =
        router.replaceScreen(FragmentScreen { CatchAMouseHumanSideFragment.newInstance() })
}