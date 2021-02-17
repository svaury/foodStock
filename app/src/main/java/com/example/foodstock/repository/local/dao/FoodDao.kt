package com.example.foodstock.repository.local.dao

import androidx.room.*
import com.example.foodstock.repository.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM stock")
    fun getAll(): List<FoodEntity>




}