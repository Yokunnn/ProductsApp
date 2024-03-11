package com.example.productsapp.domain

import com.example.productsapp.domain.model.Product
import com.vsu.newser.utils.Request
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProducts(
        skip: Int,
        limit: Int
    ): Flow<Request<List<Product>>>
}