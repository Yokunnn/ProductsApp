package com.example.productsapp.data

import com.example.productsapp.domain.ProductsRepository
import com.example.productsapp.domain.model.Product
import com.vsu.newser.utils.Request
import com.vsu.newser.utils.RequestUtils.requestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsService: ProductsService
) : ProductsRepository {
    override suspend fun getProducts(skip: Int, limit: Int): Flow<Request<List<Product>>> {
        return requestFlow {
            val products = productsService.getProducts(skip, limit)
            products.mapToDomain()
        }
    }
}