package io.github.grishaninvyacheslav.core.data.provider.time

import io.github.grishaninvyacheslav.core.data.source.time.TimeDataSource
import io.github.grishaninvyacheslav.core.utils.executeWithDelayedRetry

class TimeProviderImpl(
    private val timeApi: TimeDataSource
) : TimeProvider {
    override suspend fun getEpochSeconds(): Long =
        executeWithDelayedRetry(retries = 3, delay = 1000) {
            timeApi.getTime().unixtime
        }
}