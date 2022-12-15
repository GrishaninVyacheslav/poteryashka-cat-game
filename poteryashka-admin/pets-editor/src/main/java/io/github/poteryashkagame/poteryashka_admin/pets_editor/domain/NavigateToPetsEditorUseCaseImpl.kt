package io.github.poteryashkagame.poteryashka_admin.pets_editor.domain

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.NavigateToPetsEditorUseCase
import io.github.poteryashkagame.poteryashka_admin.pets_editor.presentation.PetsEditorFragment

class NavigateToPetsEditorUseCaseImpl(
    private val router: Router
) : NavigateToPetsEditorUseCase {
    override fun invoke(petId: String?) =
        if(!petId.isNullOrBlank()){
            router.navigateTo(FragmentScreen { PetsEditorFragment.newInstance(petId) })
        } else {
            invoke()
        }

    override fun invoke() =
        router.navigateTo(FragmentScreen { PetsEditorFragment.newInstance() })
}