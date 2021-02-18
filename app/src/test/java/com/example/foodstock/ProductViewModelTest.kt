package com.example.foodstock

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.foodstock.model.Product
import com.example.foodstock.repository.ProductRepository
import com.example.foodstock.ui.viewmodel.AddProductViewModel
import com.example.foodstock.utils.Status
import io.mockk.every
import io.mockk.mockkClass
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class ProductViewModelTest {


    private val  productRepository = mockkClass(ProductRepository::class)

    val addProductViewModel  = AddProductViewModel(productRepository)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun testSearchProductError(){
        every {
            productRepository.addOrUpdateProduct(any())
        } returns Unit

        addProductViewModel.searchProduct("","05/04/2022")

        assertEquals(addProductViewModel.searchResultLiveData.getOrAwaitValue().status,
            Status.ERROR_BARCODE )

        addProductViewModel.searchProduct("0001234567","")

        assertEquals(addProductViewModel.searchResultLiveData.getOrAwaitValue().status,
            Status.ERROR_BARCODE )

        addProductViewModel.searchProduct("000123456B000","")

        assertEquals(addProductViewModel.searchResultLiveData.getOrAwaitValue().status,
            Status.ERROR_BARCODE )

        addProductViewModel.searchProduct("0001234567000","")

        assertEquals(addProductViewModel.searchResultLiveData.getOrAwaitValue().status,
            Status.ERROR_PEREMPTION )

    }

    @Test
    fun testPostResult(){

        every {
            productRepository.addOrUpdateProduct(any())
        } returns Unit

        val product = Product(null,"123456","testProduct","",1645398000000)
        val peremptionDateLower = 1613862000000

        val data =  Data.success(product,"")

        addProductViewModel.postResult(data, peremptionDateLower)

        assertEquals(addProductViewModel.searchResultLiveData.getOrAwaitValue().status,
            Status.SUCCESS )

        val peremptionDateHighter = 1676934000000

        addProductViewModel.postResult(data, peremptionDateHighter)
        assertEquals(addProductViewModel.searchResultLiveData.getOrAwaitValue().status,
            Status.ERROR )
    }

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }


}