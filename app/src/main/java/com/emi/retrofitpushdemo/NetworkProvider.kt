package com.emi.retrofitpushdemo

import android.content.Context
import com.emi.retrofitpushdemo.CacheSources.Companion.cacheDir
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class NetworkProvider constructor(private val context: Context){


    private fun createGson() : Gson = GsonBuilder()
        .setLenient()
        .create()


    private fun httpClient() : OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .cache(cacheDir(context))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY })
        .build()


    private fun retrofit() : Retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient())
                .build()


    fun networkProvider() : NetworkServices?{
        return retrofit().create(NetworkServices::class.java)
    }

    companion object{
        const val url = "http://jsonplaceholder.typicode.com/"
    }
}