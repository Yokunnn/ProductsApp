package com.example.productsapp.data

import com.example.productsapp.data.model.ProductResponse
import com.example.productsapp.domain.model.Product

fun ProductResponse.mapToDomain(): List<Product> {
    val values: MutableList<Product> = emptyList<Product>().toMutableList()
    products.forEach {
        values.add(Product(it.title, it.description, it.thumbnail))
    }
    return values.toList()
}