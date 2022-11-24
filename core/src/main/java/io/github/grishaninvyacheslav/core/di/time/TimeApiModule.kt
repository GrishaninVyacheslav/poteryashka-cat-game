package io.github.grishaninvyacheslav.core.di.time

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.github.grishaninvyacheslav.core.BuildConfig
import io.github.grishaninvyacheslav.core.data.source.time.TimeDataSource
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val timeApiModule = module {
    single(named("baseUrl")) { provideBaseUrl() }
    single { provideTimeApi(get(named("baseUrl")), get()) }
    single { provideGson() }
}

fun provideBaseUrl(): String = BuildConfig.TIME_API_URL

fun provideTimeApi(
    baseUrl: String,
    gson: Gson
): TimeDataSource {
    val client = OkHttpClient.Builder()
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(TimeDataSource::class.java)
}

fun provideGson(): Gson = GsonBuilder().create()