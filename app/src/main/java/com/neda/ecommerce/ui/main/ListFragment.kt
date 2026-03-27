package com.neda.ecommerce.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neda.ecommerce.R
import com.neda.ecommerce.databinding.FragmentListBinding
import com.neda.ecommerce.ui.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [androidx.fragment.app.Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()

    private val adapter = ProductsAdapter(
        onClick = { productId ->
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToDetailsFragment(
                    productId
                )
            )
        },
        onFavoriteClick = { product ->
            viewModel.toggleFavorite(product)
        },
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener { _ ->
            findNavController().navigate(R.id.favoritesFragment)
        }
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                val lm = rv.layoutManager as LinearLayoutManager

                val total = lm.itemCount
                val lastVisible = lm.findLastVisibleItemPosition()

                if (lastVisible >= total - 3) {
                    viewModel.loadNextPage()
                }
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.swipeRefresh.isRefreshing = state.isInitialLoading
                    binding.progressBar.isVisible =
                        state.isInitialLoading && !binding.swipeRefresh.isRefreshing
                    binding.tvError.isVisible = state.error != null
                    binding.tvError.text = state.error
                    binding.tvEmpty.isVisible =
                        state.items.isEmpty() && !state.isInitialLoading && state.error == null
                    adapter.submitList(state.items)
                }
            }
        }

        viewModel.favorites.observe(viewLifecycleOwner) { favList ->
            adapter.updateFavorites(favList.map { it.id }.toSet())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
