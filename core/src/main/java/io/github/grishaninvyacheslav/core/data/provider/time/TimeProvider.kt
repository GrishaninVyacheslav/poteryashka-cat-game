package io.github.grishaninvyacheslav.core.data.provider.time

interface TimeProvider {
    suspend fun getEpochSeconds(): Long
}