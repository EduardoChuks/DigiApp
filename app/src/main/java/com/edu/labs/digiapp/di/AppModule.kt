package com.edu.labs.digiapp.di

import com.edu.labs.digiapp.BuildConfig
import com.edu.labs.digiapp.data.remote.api.HomeAPIs
import com.edu.labs.digiapp.data.repository.HomeRepositoryImpl
import com.edu.labs.digiapp.data.util.NetworkManager
import com.edu.labs.digiapp.domain.repository.HomeRepository
import com.edu.labs.digiapp.presentation.feature.home.HomeViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single { NetworkManager(get()) }

    single { createHttpClient() }

    single {
        createRetrofit(
            okHttpClient = get(),
            baseUrl = BuildConfig.BASE_API_URL
        )
    }

    single {
        createWebService<HomeAPIs>(retrofit = get())
    }

    single<HomeRepository> { HomeRepositoryImpl(get(), get(), get(), get()) }

    viewModel { HomeViewModel(get()) }

}

fun createRetrofit(
    okHttpClient: OkHttpClient,
    baseUrl: String
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttpClient)
        .build()
}

inline fun <reified ApiType> createWebService(retrofit: Retrofit): ApiType {
    return retrofit.create(ApiType::class.java)
}

fun createHttpClient(): OkHttpClient = createHttpClientBuilder().build()

fun createHttpClientBuilder(): OkHttpClient.Builder {
    val client = OkHttpClient.Builder()
    client.readTimeout(300, TimeUnit.SECONDS)
    client.connectTimeout(300, TimeUnit.SECONDS)
    client.addInterceptor(addLogInterceptor())
    return client
}

fun addLogInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}