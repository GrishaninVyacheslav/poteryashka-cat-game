package io.github.grishaninvyacheslav.core.di.time

import io.github.grishaninvyacheslav.core.data.provider.time.TimeProvider
import io.github.grishaninvyacheslav.core.data.provider.time.TimeProviderImpl
import io.github.grishaninvyacheslav.core.data.source.time.TimeDataSource
import org.koin.dsl.module

val timeModelsModule = module {
    single { provideTimeProvider(get()) }
}

fun provideTimeProvider(
    timeApi: TimeDataSource
): TimeProvider = TimeProviderImpl(timeApi)