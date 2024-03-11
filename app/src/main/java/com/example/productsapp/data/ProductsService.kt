package com.example.productsapp.data

import com.example.productsapp.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsService {
    @GET("products/")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): ProductResponse
}