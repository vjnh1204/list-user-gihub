package com.example.testandroid.di

import com.example.testandroid.utils.Constants
import com.example.testandroid.api.CallApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GithubApiModule {


    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): CallApi{
        return builder.build().create(CallApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder{
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor {
                val requestBuilder = it.request().newBuilder()
                if (Constants.TOKEN.isNotEmpty()){
                    requestBuilder.addHeader("Accept", Constants.RECOMMENDED_ACCEPT_HEADER)
                    requestBuilder.addHeader("Authorization", "token ${Constants.TOKEN}")
                }
                it.proceed(requestBuilder.build())
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
    }
}