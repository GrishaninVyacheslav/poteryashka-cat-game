package io.github.grishaninvyacheslav.core.data.source.time

import io.github.grishaninvyacheslav.core.data.entity.TimeEntity
import retrofit2.http.GET

interface TimeDataSource {
    @GET("timezone/Europe/London")
    suspend fun getTime(): TimeEntity
}