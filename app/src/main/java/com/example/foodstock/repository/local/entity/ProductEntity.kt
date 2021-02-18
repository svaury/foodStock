package com.example.foodstock.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "stock",indices = arrayOf(Index(value = ["bareCode"],
    unique = true)))
data class ProductEntity (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name ="bareCode") val barCode : String,
    @ColumnInfo(name = "name") val name: String ,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl : String?,
    @ColumnInfo(name = "peremptionDate") val peremptionDate : Long



) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductEntity

        if (barCode != other.barCode) return false

        return true
    }

    override fun hashCode(): Int {
        return barCode.hashCode()
    }
}