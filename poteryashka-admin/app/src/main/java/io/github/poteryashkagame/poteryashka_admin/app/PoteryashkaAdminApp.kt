package io.github.poteryashkagame.poteryashka_admin.app

import android.app.Application
import io.github.poteryashkagame.poteryashka_admin.app.di.mainViewModelsModule
import io.github.poteryashkagame.poteryashka_admin.details.di.detailsUseCasesModule
import io.github.poteryashkagame.poteryashka_admin.details.di.detailsViewModelsModule
import io.github.poteryashkagame.poteryashka_admin.list.di.listUseCasesModule
import io.github.poteryashkagame.poteryashka_admin.list.di.listViewModelsModule
import io.github.poteryashkagame.poteryashka_admin.navigation.di.navigationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PoteryashkaAdminApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PoteryashkaAdminApp)
            modules(
                navigationModule,

                mainViewModelsModule,

                listUseCasesModule,
                listViewModelsModule,

                detailsUseCasesModule,
                detailsViewModelsModule
            )
        }
    }
}