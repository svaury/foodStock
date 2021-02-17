package com.example.foodstock.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
    val id: Int,
    val barCode : String,
    val name: String,
    val imageUrl:String?,
    var peremptionDate: Long
): Parcelable