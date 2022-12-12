package io.github.poteryashkagame.poteryashka_admin.navigation.domain.use_cases

interface NavigateToDetailsUseCase {
    operator fun invoke(detailsId: String)
}