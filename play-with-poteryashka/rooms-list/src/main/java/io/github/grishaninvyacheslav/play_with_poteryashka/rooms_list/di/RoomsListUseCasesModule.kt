package io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.di

import com.github.terrakok.cicerone.Router
import io.github.grishaninvyacheslav.navigation.domain.use_cases.NavigateToRoomsListUseCase
import io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.domain.use_cases.NavigateToRoomsListUseCaseImpl
import org.koin.dsl.module

val roomsListUseCasesModule = module {
    factory { provideNavigateToRoomsList(get()) }
}

fun provideNavigateToRoomsList(router: Router): NavigateToRoomsListUseCase =
    NavigateToRoomsListUseCaseImpl(router)
