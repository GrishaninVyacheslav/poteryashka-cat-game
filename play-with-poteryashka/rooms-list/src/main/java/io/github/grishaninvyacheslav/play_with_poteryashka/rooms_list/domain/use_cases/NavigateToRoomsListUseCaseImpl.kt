package io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.domain.use_cases

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToRoomsListUseCase
import io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.presentation.RoomsListFragment

class NavigateToRoomsListUseCaseImpl(
    private val router: Router
) : NavigateToRoomsListUseCase {
    override fun invoke() =
        router.replaceScreen(FragmentScreen { RoomsListFragment.newInstance() })
}