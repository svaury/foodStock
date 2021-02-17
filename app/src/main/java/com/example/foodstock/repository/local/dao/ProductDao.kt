package com.example.foodstock.repository.local.dao

import androidx.room.*
import com.example.foodstock.repository.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM stock")
    fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM stock WHERE bareCode = :strBarCode")
    fun findProductByBarCode(strBarCode: String): ProductEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(productEntity: ProductEntity): Long


}