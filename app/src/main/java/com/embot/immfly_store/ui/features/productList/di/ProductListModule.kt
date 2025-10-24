package com.embot.immfly_store.ui.features.productList.di

import com.embot.immfly_store.domain.service.DispatchersProvider
import com.embot.immfly_store.domain.service.remoteSource.IProductService
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
        dispatchersProvider: DispatchersProvider,
        localDatabase: ProductDao,
        productService: IProductService
    ): IProductListRepository {
        return ProductListRepository(
            dispatchersProvider = dispatchersProvider,
            localDatabase = localDatabase,
            productService = productService
        )
    }

}