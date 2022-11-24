package io.github.grishaninvyacheslav.poteryashka_playground

import android.app.Application
import io.github.grishaninvyacheslav.core.di.time.timeApiModule
import io.github.grishaninvyacheslav.core.di.time.timeModelsModule
import io.github.grishaninvyacheslav.game_catch_a_mouse.di.catchAMouseUseCasesModule
import io.github.grishaninvyacheslav.game_catch_a_mouse.di.catchAMouseViewModelsModule
import io.github.grishaninvyacheslav.poteryashka_playground.di.firebaseCloudFirestoreModule
import io.github.grishaninvyacheslav.poteryashka_playground.di.firebaseRealtimeDatabaseModule
import io.github.grishaninvyacheslav.poteryashka_playground.di.mainViewModelsModule
import io.github.grishaninvyacheslav.poteryashka_playground.navigation.di.navigationModule
import io.github.grishaninvyacheslav.poteryashka_playground.playground.di.playgroundUseCasesModule
import io.github.grishaninvyacheslav.poteryashka_playground.playground.di.playgroundViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PoteryashkaPlaygroundApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PoteryashkaPlaygroundApp)
            modules(
                navigationModule,
                mainViewModelsModule,
                firebaseRealtimeDatabaseModule,
                firebaseCloudFirestoreModule,
                timeApiModule,
                timeModelsModule,
                playgroundViewModelModule,
                playgroundUseCasesModule,
                catchAMouseUseCasesModule,
                catchAMouseViewModelsModule
            )
        }
    }
}