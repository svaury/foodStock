package com.example.foodstock.repository.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface GetProductService {

    @GET("v0/product/{barCode}")
    suspend fun getProductByBarCode(@Path("barCode") barCode: String): ProductModelJson
}