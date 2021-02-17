package com.example.foodstock.repository

import com.example.foodstock.model.Product
import com.example.foodstock.repository.local.dao.ProductDao
import com.example.foodstock.repository.mapper.ToProductModelWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject

class ProductRepository(var productDao: ProductDao){

    fun getProductList(): Flow<List<Product>> = flow {
       val result =  productDao.getAll().map { ToProductModelWrapper().entityToProductModel(it)}
        emit(result)
    }
}