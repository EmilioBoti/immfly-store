package com.embot.immfly_store.domain.service.localResource.localDatabase

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.embot.immfly_store.domain.models.roomEntity.ProductEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductDaoTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var productDatabase: ProductDatabase
    private lateinit var dao: ProductDao


    @Before
    fun setup() {
        productDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ProductDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = productDatabase.productDao()
    }


    @After
    fun tearDown() {
        productDatabase.close()
    }

    @Test
    fun update_product_quantity() = runTest {
        //GIVEN
        val quantity = 3
        val productEntity = ProductEntity(
            id = "1",
            name = "Product 1",
            description = "Description 1",
            price = 10.0,
            image = "",
            quantity = 1,
            stock = 6
        )
        //WHEN
        dao.storeToCart(productEntity)
        dao.updateProductQuantity("1", quantity)
        val products = dao.getCartProducts()
        assertThat(products[0].quantity).isEqualTo(quantity)
    }

    @Test
    fun clear_cart() = runTest {
        val productEntity = ProductEntity(
            id = "1",
            name = "Product 1",
            description = "Description 1",
            price = 10.0,
            image = "",
            quantity = 1,
            stock = 6
        )
        //WHEN
        dao.storeToCart(productEntity)
        val products = dao.getCartProducts()
        assertThat(products).isEmpty()
    }


    @Test
    fun delete_product_from_cart() = runTest {
        val productEntity = ProductEntity(
            id = "1",
            name = "Product 1",
            description = "Description 1",
            price = 10.0,
            image = "",
            quantity = 1,
            stock = 6
        )
        //WHEN
        dao.storeToCart(productEntity)
        dao.deleteFromCart(id = "1")

        val products = dao.getCartProducts()
        assertThat(products).doesNotContain(productEntity)
    }

    @Test
    fun return_list_of_cart_product_should_have_product_inserted() = runTest {
        val productEntity = ProductEntity(
            id = "1",
            name = "Product 1",
            description = "Description 1",
            price = 10.0,
            image = "",
            quantity = 1,
            stock = 6
        )
        //WHEN
        dao.storeToCart(productEntity)

        val products = dao.getCartProducts()
        assertThat(products).contains(productEntity)
    }

    @Test
    fun return_the_list_of_cart_product() = runTest {
        val productEntity = ProductEntity(
            id = "1",
            name = "Product 1",
            description = "Description 1",
            price = 10.0,
            image = "",
            quantity = 1,
            stock = 6
        )
        //WHEN
        dao.storeToCart(productEntity)

        val products = dao.getCartProducts()
        assertThat(products).isNotEmpty()
    }

    @Test
    fun store_product() = runTest {
        //Given
        val productEntity = ProductEntity(
            id = "1",
            name = "Product 1",
            description = "Description 1",
            price = 10.0,
            image = "",
            quantity = 1,
            stock = 6
        )
        //WHEN
        dao.storeToCart(productEntity)
        val products = dao.getCartProducts()
        assertThat(products).contains(productEntity)
    }


}