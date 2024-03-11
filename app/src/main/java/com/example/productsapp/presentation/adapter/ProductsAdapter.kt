package com.example.productsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapp.databinding.ProductItemBinding
import com.example.productsapp.domain.model.Product

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var items: MutableList<Product> = emptyList<Product>().toMutableList()

    inner class ProductViewHolder(
        binding: ProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val titleTv = binding.titleTv
        val descriptionTv = binding.descriptionTv
        val thumbnailIv = binding.thumbnailIv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = items[position]

        holder.titleTv.text = data.title
        holder.descriptionTv.text = data.description
        holder.thumbnailIv.setImageURI(data.thumbnail)
    }

    fun update(data: MutableList<Product>) {
        items = data
        notifyDataSetChanged()
    }
}