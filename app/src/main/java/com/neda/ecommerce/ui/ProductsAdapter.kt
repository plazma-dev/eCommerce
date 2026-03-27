package com.neda.ecommerce.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neda.ecommerce.data.model.Product
import com.neda.ecommerce.databinding.ItemProductBinding
import com.neda.ecommerce.R

class ProductsAdapter(
    private val onClick: (Int) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) :
    ListAdapter<Product, ProductsAdapter.ProductViewHolder>(DiffCallback) {

    private var favoriteIds: Set<Int> = emptySet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding, onClick, onFavoriteClick) { id ->
            favoriteIds.contains(id)
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateFavorites(newList: Set<Int>) {
        favoriteIds = newList
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onClick: (Int) -> Unit,
        private val onFavoriteClick: (Product) -> Unit,
        private val isFavorite: (Int) -> Boolean
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.tvTitle.text = "${item.title}"
            binding.tvPrice.text = "$${item.price}"
            Glide.with(binding.root.context)
                .load(item.thumbnail)
                .centerCrop()
                .into(binding.ivProduct)
            binding.ivFavorite.setImageResource(if(isFavorite(item.id)) R.drawable.favorite_on else R.drawable.favorite_off)

            binding.ivFavorite.setOnClickListener {
                onFavoriteClick(item)
            }

            binding.root.setOnClickListener {
                onClick(item.id)
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Product>() {

            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}