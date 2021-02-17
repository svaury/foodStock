package com.example.foodstock.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock")
data class FoodEntity (
    @PrimaryKey val id: Int,
    @ColumnInfo(name ="bareCode") val barCode : String,
    @ColumnInfo(name = "name") val name: String ,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl : String
)