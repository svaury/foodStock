package com.example.foodstock

import com.example.foodstock.repository.ProductRepository
import com.example.foodstock.repository.local.dao.ProductDao
import com.example.foodstock.repository.local.entity.ProductEntity
import com.example.foodstock.repository.remote.GetProductService
import com.example.foodstock.repository.remote.ProductJson
import com.example.foodstock.repository.remote.ProductModelJson
import com.example.foodstock.utils.Status
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class ProductRepositoryTest {

    private val  productService = mockkClass(GetProductService::class)

    private val productDao = mockkClass(ProductDao::class)

    @Test
    fun getProductByBarCodeTest()= runBlockingTest {


        val productRepository = ProductRepository(productDao,productService)
        val productJson = ProductJson("test","1234567891234","")
        val productModelJson = ProductModelJson(productJson,"1234567891234","",1)

        val productEntity = ProductEntity(1,"1234567891456","testDb","",0)

        val productModelJsonError = ProductModelJson(null,"1234567891234","",1)

        coEvery {
            productService.getProductByBarCode(any())
        } returns productModelJson

        every {
            productDao.findProductByBarCode(any())
        } returns null


       var product =  productRepository.getProductByBarCode("1234567891234")?.toList()

        Assert.assertEquals(product!![0].status, Status.SUCCESS )

        Assert.assertEquals(product!![0].data?.barCode,productModelJson.barCode )


        coEvery {
            productService.getProductByBarCode(any())
        } returns productModelJsonError

         product = productRepository.getProductByBarCode("1234567891234")?.toList()

        Assert.assertEquals(product!![0].status, Status.ERROR )


        every {
            productDao.findProductByBarCode(any())
        } returns productEntity

        product = productRepository.getProductByBarCode("1234567891234")?.toList()

        Assert.assertEquals(product!![0].status, Status.SUCCESS )

        Assert.assertEquals(product!![0].data?.barCode,productEntity.barCode )


    }
}