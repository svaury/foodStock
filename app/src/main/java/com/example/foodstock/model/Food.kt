package com.example.foodstock.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food (
    val id: Int,
    val barCode : String,
    val name: String,
    val imageUrl:String
): Parcelable