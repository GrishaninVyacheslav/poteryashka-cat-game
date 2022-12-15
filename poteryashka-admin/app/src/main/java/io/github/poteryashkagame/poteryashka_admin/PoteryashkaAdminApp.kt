package io.github.poteryashkagame.poteryashka_admin

import android.app.Application
import io.github.poteryashkagame.poteryashka_admin.app.di.firebaseModule
import io.github.poteryashkagame.poteryashka_admin.app.di.mainViewModelsModule
import io.github.poteryashkagame.poteryashka_admin.details.di.detailsUseCasesModule
import io.github.poteryashkagame.poteryashka_admin.details.di.detailsViewModelsModule
import io.github.poteryashkagame.poteryashka_admin.list.di.listUseCasesModule
import io.github.poteryashkagame.poteryashka_admin.list.di.listViewModelsModule
import io.github.poteryashkagame.poteryashka_admin.navigation.di.navigationModule
import io.github.poteryashkagame.poteryashka_admin.pets_editor.di.petsEditorUseCasesModule
import io.github.poteryashkagame.poteryashka_admin.pets_editor.di.petsEditorViewModelsModule
import io.github.poteryashkagame.poteryashka_admin.pets_list.di.petsListAdaptersModule
import io.github.poteryashkagame.poteryashka_admin.pets_list.di.petsListUseCaseModule
import io.github.poteryashkagame.poteryashka_admin.pets_list.di.petsListViewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PoteryashkaAdminApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PoteryashkaAdminApp)
            modules(
                firebaseModule,

                navigationModule,

                mainViewModelsModule,

                petsListUseCaseModule,
                petsListAdaptersModule,
                petsListViewModelsModule,

                petsEditorUseCasesModule,
                petsEditorViewModelsModule,

                listUseCasesModule,
                listViewModelsModule,

                detailsUseCasesModule,
                detailsViewModelsModule
            )
        }
    }
}