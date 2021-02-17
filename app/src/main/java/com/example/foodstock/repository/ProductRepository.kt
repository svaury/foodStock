package com.example.foodstock.repository

import com.example.foodstock.model.Product
import com.example.foodstock.repository.local.dao.ProductDao
import com.example.foodstock.repository.mapper.ToProductModelWrapper
import org.koin.java.KoinJavaComponent.inject

class ProductRepository(var productDao: ProductDao){

    fun getProductList(): List<Product>{
        return productDao.getAll().map { ToProductModelWrapper().entityToProductModel(it)};
    }
}