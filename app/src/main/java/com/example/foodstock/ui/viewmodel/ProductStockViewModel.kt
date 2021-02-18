package com.example.foodstock.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodstock.model.Product
import com.example.foodstock.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductStockViewModel(val productRepository: ProductRepository): ViewModel() {

    init {
        getProducts()
    }

    val productListLiveData : MutableLiveData<List<Product>> = MutableLiveData()


    private fun getProducts(){

        GlobalScope.launch(viewModelScope.coroutineContext + Dispatchers.IO){

            productRepository.getProductList().collect{ value -> productListLiveData.postValue(value) }
        }
    }

    fun removeProducts(product: Product){

        GlobalScope.launch(viewModelScope.coroutineContext + Dispatchers.IO){

            productRepository.removeProduct(product)
        }
    }
}