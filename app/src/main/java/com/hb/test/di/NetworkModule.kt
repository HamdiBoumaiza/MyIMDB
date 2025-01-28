package com.hb.test.di

import android.content.Context
import com.hb.test.BuildConfig
import com.hb.test.data.RemoteApi
import com.hb.test.utils.network.NetworkMonitor
import com.hb.test.utils.network.NetworkMonitorImpl
import com.hb.test.utils.network.NetworkMonitorInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext appContext: Context): NetworkMonitor {
        return NetworkMonitorImpl(appContext)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
        @AuthorizationInterceptor authorizationInterceptor: Interceptor,
        liveNetworkMonitor: NetworkMonitor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(NetworkMonitorInterceptor(liveNetworkMonitor))
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @AuthorizationInterceptor
    fun provideAuthorizationInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request()
                .newBuilder()
                .addHeader(AUTHORIZATION, "$BEARER ${BuildConfig.TMDB_AUTH_TOKEN}")
                .addHeader(ACCEPT, APP_JSON)
                .build()
            return@Interceptor it.proceed(request)
        }
    }

    @Provides
    @Singleton
    @LoggingInterceptor
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideRemoteApi(retrofit: Retrofit.Builder): RemoteApi =
        retrofit.build().create(RemoteApi::class.java)

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer"
        const val ACCEPT = "accept"
        const val APP_JSON = "application/json"
    }
}
