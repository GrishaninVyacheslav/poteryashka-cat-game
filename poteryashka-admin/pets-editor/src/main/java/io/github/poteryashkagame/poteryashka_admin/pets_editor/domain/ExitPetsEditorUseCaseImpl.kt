package io.github.poteryashkagame.poteryashka_admin.pets_editor.domain

import com.github.terrakok.cicerone.Router
import io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases.ExitPetsEditorUseCase

class ExitPetsEditorUseCaseImpl(
    private val router: Router
) : ExitPetsEditorUseCase {
    override fun invoke() =
        router.exit()
}