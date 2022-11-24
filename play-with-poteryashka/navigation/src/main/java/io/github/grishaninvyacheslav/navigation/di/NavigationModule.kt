package io.github.grishaninvyacheslav.navigation.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

val navigationModule = module {
    single { provideCicerone() }
    single { provideRouter(get()) }
    single { provideNavigatorHolder(get()) }
}

fun provideCicerone(): Cicerone<Router> = Cicerone.create()
fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router
fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()