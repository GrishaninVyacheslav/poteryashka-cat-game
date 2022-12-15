package io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases

interface NavigateToPetsEditorUseCase {
    operator fun invoke(petId: String?)
    operator fun invoke()
}