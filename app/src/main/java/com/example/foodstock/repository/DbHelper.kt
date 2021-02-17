package com.example.foodstock.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodstock.repository.local.dao.ProductDao
import com.example.foodstock.repository.local.entity.ProductEntity


@Database(entities = [ProductEntity::class], version = 1)
abstract class DbHelper : RoomDatabase() {
    abstract fun productDao(): ProductDao
}