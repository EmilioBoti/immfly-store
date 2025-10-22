package com.embot.immfly_store.domain.di

import android.content.Context
import com.embot.immfly_store.BuildConfig
import com.embot.immfly_store.domain.service.IProductService
import com.embot.immfly_store.domain.service.localResource.localDatabase.ProductDao
import com.embot.immfly_store.domain.service.localResource.localDatabase.ProductDatabase
import com.embot.immfly_store.domain.service.localResource.preference.AppDataStore
import com.embot.immfly_store.domain.service.localResource.preference.IAppDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Qualifier
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideAppDataStore(
        @ApplicationContext context: Context
    ): IAppDataStore = AppDataStore(context)


    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): ProductDao = ProductDatabase.getDatabaseInstance(context).productDao()


    @ProductApiHttpClient
    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    fun provideRetrofit(@ProductApiHttpClient client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): IProductService {
        return retrofit
            .create(IProductService::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ProductApiHttpClient

}