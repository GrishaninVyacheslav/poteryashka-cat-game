package io.github.grishaninvyacheslav.play_with_poteryashka

import android.app.Application
import io.github.grishaninvyacheslav.core.di.time.timeApiModule
import io.github.grishaninvyacheslav.core.di.time.timeModelsModule
import io.github.grishaninvyacheslav.game_catch_a_mouse.di.catchAMouseUseCasesModule
import io.github.grishaninvyacheslav.game_catch_a_mouse.di.catchAMouseViewModelsModule
import io.github.grishaninvyacheslav.human_playground.di.humanPlaygroundDataModule
import io.github.grishaninvyacheslav.human_playground.di.humanPlaygroundUseCasesModule
import io.github.grishaninvyacheslav.human_playground.di.humanPlaygroundViewModelsModule
import io.github.grishaninvyacheslav.navigation.di.navigationModule
import io.github.grishaninvyacheslav.play_with_poteryashka.di.firebaseCloudFirestoreModule
import io.github.grishaninvyacheslav.play_with_poteryashka.di.firebaseRealtimeDatabaseModule
import io.github.grishaninvyacheslav.play_with_poteryashka.di.mainViewModelsModule
import io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.di.roomsListUseCasesModule
import io.github.grishaninvyacheslav.play_with_poteryashka.rooms_list.di.roomsListViewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PlayWithPoteryashkaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlayWithPoteryashkaApp)
            modules(
                mainViewModelsModule,

                catchAMouseUseCasesModule,
                catchAMouseViewModelsModule,

                timeApiModule,
                timeModelsModule,

                humanPlaygroundDataModule,
                humanPlaygroundUseCasesModule,
                humanPlaygroundViewModelsModule,

                roomsListViewModelsModule,
                roomsListUseCasesModule,

                navigationModule,

                firebaseRealtimeDatabaseModule,
                firebaseCloudFirestoreModule,
            )
        }
    }
}