package com.embot.immfly_store.domain.di

import com.embot.immfly_store.domain.service.DispatchersProvider
import com.embot.immfly_store.domain.service.StandarDispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideDispacherProvider(): DispatchersProvider = StandarDispatchersProvider

}