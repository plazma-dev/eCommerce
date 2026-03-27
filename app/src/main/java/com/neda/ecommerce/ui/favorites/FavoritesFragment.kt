package com.neda.ecommerce.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.neda.ecommerce.R
import com.neda.ecommerce.databinding.FragmentFavoritesBinding
import com.neda.ecommerce.ui.ProductsAdapter
import com.neda.ecommerce.utils.toFavorite
import com.neda.ecommerce.utils.toProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModels()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)

        val adapter = ProductsAdapter(onFavoriteClick = { product ->
            viewModel.removeFavorite(product.toFavorite())
        }, onClick = {})

        binding.rvFavorites.adapter = adapter

        viewModel.favorites.observe(viewLifecycleOwner) { favoriteList ->
            if (favoriteList.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvFavorites.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvFavorites.visibility = View.VISIBLE

                val products = favoriteList.map { it.toProduct() }
                adapter.submitList(products)
                adapter.updateFavorites(products.map { it.id }.toSet())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}