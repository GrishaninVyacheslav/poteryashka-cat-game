package io.github.poteryashkagame.poteryashka_admin.details.domain.use_cases

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.poteryashkagame.poteryashka_admin.details.presentation.DetailsFragment
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToDetailsUseCase

class NavigateToDetailsUseCaseImpl(
    private val router: Router
) : NavigateToDetailsUseCase {
    override fun invoke(detailsId: String) =
        router.navigateTo(FragmentScreen { DetailsFragment.newInstance(detailsId) })
}