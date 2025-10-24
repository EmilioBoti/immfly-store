package com.embot.immfly_store.ui.features.cartProducts.di

import com.embot.immfly_store.domain.service.DispatchersProvider
import com.embot.immfly_store.domain.service.remoteSource.IProductService
import com.embot.immfly_store.domain.service.localResource.localDatabase.ProductDao
import com.embot.immfly_store.ui.features.cartProducts.repository.CartProductRepository
import com.embot.immfly_store.ui.features.cartProducts.repository.ICartProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CartProductModule {


    @Provides
    @Singleton
    fun provideCartProductRepository(
        dispatchersProvider: DispatchersProvider,
        localDatabase: ProductDao,
        productService: IProductService
    ): ICartProductRepository {
        return CartProductRepository(
            dispatchersProvider = dispatchersProvider,
            localDatabase = localDatabase,
            productService = productService
        )
    }


}