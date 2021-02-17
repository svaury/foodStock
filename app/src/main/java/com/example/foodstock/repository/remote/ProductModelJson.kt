package com.example.foodstock.repository.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModelJson(
    @SerializedName("packaging")
    val name: String,
    @SerializedName("code")
    val barCode : String,
    @SerializedName("image_thumb_url")
    val imageUrl : String?

): Parcelable


