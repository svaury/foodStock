package com.example.foodstock.repository

import android.util.Log
import com.example.foodstock.Data
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

    fun getProductByBarCode(barCode : String): Flow<Data<Product>>? = flow{
        val result = productDao.findProductByBarCode(barCode)
        result?.let {
            emit(Data.success(ToProductModelMapper().entityToProductModel(result),""))
        }?:let{
            try {
                val productFromRemote = getProductService.getProductByBarCode(barCode)
                productFromRemote.product?.let {
                    emit(Data.success(ToProductModelMapper().jsonToProductModel(productFromRemote),""))

                }?:let {
                    emit(Data.error(null, productFromRemote.statusMessage?: ""))

                }
            }catch(exception: Exception){
                emit(Data.error(null,"Une erreur est survenue"))

            }
        }
    }

    fun addOrUpdateProduct(product: Product){
        Log.i("Add Product ","Add product ")

        val result = productDao.insertProduct(ToProductEntityMapper().entityToProductModel(product))

        Log.i("Add Product ","Add product "+ result)

    }
}