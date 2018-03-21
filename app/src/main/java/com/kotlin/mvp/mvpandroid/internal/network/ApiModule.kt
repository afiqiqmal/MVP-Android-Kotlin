package com.kotlin.mvp.mvpandroid.internal.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.java.mvp.factory.internal.RestApi
import com.kotlin.mvp.mvpandroid.prefs.PreferencesRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

/**
 * @author : hafiq on 19/08/2017.
 */

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.setLenient()
        builder.registerTypeAdapter(Date::class.java, JsonDeserializer<Date> {
            json, _, _ ->
            if (json.asJsonPrimitive.isNumber)
                Date(json.asJsonPrimitive.asLong * 1000) else
                null
        })
        return builder.create()
    }

    @Provides
    @Singleton
    fun provideRestApi(client: OkHttpClient, g: Gson, preferencesRepository: PreferencesRepository): RestApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(preferencesRepository.url)
                .addConverterFactory(GsonConverterFactory.create(g))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

        return retrofit.create(RestApi::class.java)
    }
}
