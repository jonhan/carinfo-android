package com.hjonas.data

import com.hjonas.data.services.carattributes.CarAttributesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Builds and hold instance to [Retrofit] and [OkHttpClient] and creates instances of the available API interfaces
 */
object ApiManager {

    /**
     * When set to true, all output from OkHttp network requests will be logged
     */
    var debuggable: Boolean = false

    private val retrofit: Retrofit by lazy { buildRetrofit() }

    private fun buildRetrofit(): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(buildOkHttpClient())
                    .build()

    private fun buildOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (debuggable) {
            val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(logger)
        }

        return builder.build()
    }

    // Public RetroFit services
    val carAttributesService: CarAttributesService by lazy { retrofit.create(CarAttributesService::class.java) }
}