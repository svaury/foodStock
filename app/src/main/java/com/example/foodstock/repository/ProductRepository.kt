package com.example.foodstock.repository

import com.example.foodstock.model.Product
import com.example.foodstock.repository.local.dao.ProductDao
import com.example.foodstock.repository.mapper.ToProductEntityMapper
import com.example.foodstock.repository.mapper.ToProductModelMapper
import com.example.foodstock.repository.remote.GetProductService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository(var productDao: ProductDao, var getProductService: GetProductService){

    fun getProductList(): Flow<List<Product>> = flow {
       val result =  productDao.getAll().map { ToProductModelMapper().entityToProductModel(it)}
        emit(result)
    }

    fun getProductByBarCode(barCode : String): Flow<Product>? = flow{
        val result = productDao.findProductByBarCode(barCode)
        result?.let {
            emit(ToProductModelMapper().entityToProductModel(result))
        }?:let{
            val productFromRemote = getProductService.getProductByBarCode(barCode)
            emit(ToProductModelMapper().jsonToProductModel(productFromRemote))
        }
    }


    fun addOrUpdateProduct(product: Product) : Flow<Long>? = flow{
        val result = productDao.insertProduct(ToProductEntityMapper().entityToProductModel(product))
        emit(result)
    }
}