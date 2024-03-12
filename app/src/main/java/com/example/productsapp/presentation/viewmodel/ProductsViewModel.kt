package com.example.productsapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsapp.data.ProductsRepositoryImpl
import com.example.productsapp.domain.model.Product
import com.example.productsapp.utils.AppConstants
import com.vsu.newser.utils.LoadState
import com.vsu.newser.utils.Request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepositoryImpl
) : ViewModel() {
    var productsResult = MutableLiveData<List<Product>?>()
    var productsLoadState = MutableLiveData<LoadState>()

    fun getProducts(skip: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productsRepository.getProducts(skip, AppConstants.PRODUCTS_LIMIT).collect { requestState ->
                when (requestState) {
                    is Request.Error -> {
                        productsLoadState.postValue(LoadState.ERROR)
                    }

                    is Request.Loading -> {
                        productsLoadState.postValue(LoadState.LOADING)
                    }

                    is Request.Success -> {
                        productsLoadState.postValue(LoadState.SUCCESS)
                        productsResult.postValue(requestState.data)
                    }
                }
            }
        }
    }
}