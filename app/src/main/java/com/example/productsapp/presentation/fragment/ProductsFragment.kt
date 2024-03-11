package com.example.productsapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productsapp.databinding.FragmentProductsBinding
import com.example.productsapp.presentation.adapter.ProductsAdapter
import com.example.productsapp.presentation.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    private lateinit var binding: FragmentProductsBinding
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

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
    }

    private fun initViews() {
        with(binding.productsRv) {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }
        viewModel.getProducts(0)
    }

    private fun observeProducts() {
        viewModel.productsResult.observe(viewLifecycleOwner) { products ->
            if (products != null) {
                productsAdapter.update(products.toMutableList())
            }
        }
    }
}