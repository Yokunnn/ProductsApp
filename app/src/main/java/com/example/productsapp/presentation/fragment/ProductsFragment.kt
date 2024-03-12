package com.example.productsapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapp.R
import com.example.productsapp.databinding.FragmentProductsBinding
import com.example.productsapp.presentation.adapter.ProductsAdapter
import com.example.productsapp.presentation.viewmodel.ProductsViewModel
import com.example.productsapp.utils.AppConstants
import com.google.android.material.snackbar.Snackbar
import com.vsu.newser.utils.LoadState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter
    private var state = LoadState.SUCCESS
    private var lastPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        productsAdapter = ProductsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeProducts()
        observeProductsLoad()
    }

    private fun initViews() {
        with(binding.productsRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter

            val layoutManager = layoutManager as LinearLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val itemsCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (state == LoadState.SUCCESS
                        && itemsCount == lastVisibleItem + 1
                        && itemsCount / AppConstants.PRODUCTS_LIMIT == lastPage
                    ) {
                        viewModel.getProducts(lastPage * AppConstants.PRODUCTS_LIMIT)
                        lastPage++
                    }
                }
            })
        }
        viewModel.getProducts(lastPage * AppConstants.PRODUCTS_LIMIT)
        lastPage++
    }

    private fun observeProducts() {
        viewModel.productsResult.observe(viewLifecycleOwner) { products ->
            if (products != null) {
                productsAdapter.update(products.toMutableList())
            }
        }
    }

    private fun observeProductsLoad() {
        viewModel.productsLoadState.observe(viewLifecycleOwner) { loadState ->
            when (loadState) {
                LoadState.LOADING -> {
                    state = LoadState.LOADING
                    createSnackbar(
                        resources.getString(R.string.loading_message),
                        resources.getColor(R.color.black, null)
                    )
                }

                LoadState.ERROR -> {
                    state = LoadState.ERROR
                    createSnackbar(
                        resources.getString(R.string.error_message),
                        resources.getColor(R.color.red, null)
                    )
                }

                LoadState.SUCCESS -> {
                    state = LoadState.SUCCESS
                    createSnackbar(
                        resources.getString(R.string.success_message),
                        resources.getColor(R.color.green, null)
                    )
                }
            }
        }
    }

    private fun createSnackbar(message: String, color: Int) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(color).show()
    }
}