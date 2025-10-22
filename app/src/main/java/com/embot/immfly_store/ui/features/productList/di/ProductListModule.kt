package com.embot.immfly_store.ui.features.productList.di

import com.embot.immfly_store.domain.service.IProductService
import com.embot.immfly_store.domain.service.localResource.localDatabase.ProductDao
import com.embot.immfly_store.ui.features.productList.repository.IProductListRepository
import com.embot.immfly_store.ui.features.productList.repository.ProductListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ProductListModule {


    @Provides
    fun provideProductListRepository(
        localDatabase: ProductDao,
        productService: IProductService
    ): IProductListRepository {
        return ProductListRepository(
            localDatabase = localDatabase,
            productService = productService
        )
    }

}