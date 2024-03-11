package com.example.productsapp.di

import com.example.productsapp.data.ProductsRepositoryImpl
import com.example.productsapp.data.ProductsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {
    @Singleton
    @Provides
    fun provideProductsService(retrofit: Retrofit): ProductsService =
        retrofit.create(ProductsService::class.java)

    @Singleton
    @Provides
    fun provideProductsRepository(productsService: ProductsService) =
        ProductsRepositoryImpl(productsService)
}