package com.example.foodstock.repository.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModelJson(
    @SerializedName("product")
    val product : ProductJson?,
    @SerializedName("code")
    val barCode : String,
    @SerializedName("status_verbose")
    val statusMessage : String,
    @SerializedName("status")
    val status : Int

): Parcelable


