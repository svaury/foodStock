package com.example.foodstock.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
    val id: Int?,
    val barCode : String,
    val name: String,
    val imageUrl:String?,
    var peremptionDate: Long
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (barCode != other.barCode) return false

        return true
    }

    override fun hashCode(): Int {
        return barCode.hashCode()
    }
}