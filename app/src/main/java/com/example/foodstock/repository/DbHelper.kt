package com.example.foodstock.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodstock.repository.local.dao.FoodDao
import com.example.foodstock.repository.local.entity.FoodEntity


@Database(entities = [FoodEntity::class], version = 1)
abstract class DbHelper : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}