package io.github.poteryashkagame.poteryashka_admin.list.domain.use_cases

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.poteryashkagame.poteryashka_admin.list.presentation.ListFragment
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToListUseCase

class NavigateToListUseCaseImpl(
    private val router: Router
) : NavigateToListUseCase {
    override fun invoke() =
        router.replaceScreen(FragmentScreen { ListFragment.newInstance() })
}