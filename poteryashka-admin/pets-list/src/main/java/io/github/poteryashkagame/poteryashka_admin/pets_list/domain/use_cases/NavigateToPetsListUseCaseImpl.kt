package io.github.poteryashkagame.poteryashka_admin.pets_list.domain.use_cases

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToPetsListUseCase
import io.github.poteryashkagame.poteryashka_admin.pets_list.presentation.PetsListFragment

class NavigateToPetsListUseCaseImpl(
    private val router: Router
) : NavigateToPetsListUseCase {
    override fun invoke() {
        router.navigateTo(FragmentScreen { PetsListFragment.newInstance() })
    }
}