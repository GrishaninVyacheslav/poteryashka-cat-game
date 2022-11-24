package io.github.grishaninvyacheslav.human_playground.domain.use_cases.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.grishaninvyacheslav.human_playground.presentation.donation.DonationFragment
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToDonationUseCase

class NavigateToDonationUseCaseImpl(
    private val router: Router
) : NavigateToDonationUseCase {
    override fun invoke() =
        router.navigateTo(FragmentScreen { DonationFragment.newInstance() })
}