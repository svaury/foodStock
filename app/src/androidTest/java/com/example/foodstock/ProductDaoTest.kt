package com.example.foodstock

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.foodstock.repository.DbHelper
import com.example.foodstock.repository.local.dao.ProductDao
import com.example.foodstock.repository.local.entity.ProductEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {
    /*
     * Inject needed components from Koin
     */
    lateinit  var  dbHelper : DbHelper
    lateinit  var  productDao : ProductDao


    /**
     * Override default Koin configuration to use Room in-memory database
     */
    @Before()
    fun before() {

        dbHelper =  Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext, DbHelper::class.java)
            .allowMainThreadQueries()
            .build()
        productDao = dbHelper.productDao()
    }

    /**
     * Close resources
     */
    @After
    fun after() {
        dbHelper.close()
    }

    @Test
    fun testSave() {
        val productEntity = ProductEntity(1,"123456789123","toto","",1613862000000L)

        // Save entities
        productDao.insertProduct(productEntity)


        // Request one entity per id
        val requestedEntities = productDao.findProductByBarCode("123456789123")

        // compare result
        Assert.assertEquals(productEntity, requestedEntities)
    }


}



