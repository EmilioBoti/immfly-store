package com.embot.immfly_store.ui.features.cartProducts.di

import com.embot.immfly_store.domain.service.IProductService
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
        localDatabase: ProductDao,
        productService: IProductService
    ): ICartProductRepository {
        return CartProductRepository(
            localDatabase = localDatabase,
            productService = productService
        )
    }


}