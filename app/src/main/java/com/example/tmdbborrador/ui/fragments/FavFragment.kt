package com.example.tmdbborrador.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbborrador.adapters.FavoritesMoviesAdapter
import com.example.tmdbborrador.databinding.FragmentFavBinding
import com.example.tmdbborrador.ui.activities.MainActivity
import com.example.tmdbborrador.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavFragment : Fragment() {

    private lateinit var binding: FragmentFavBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var favoritesMoviesAdapter: FavoritesMoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentMovie = favoritesMoviesAdapter.differ.currentList[position]
                homeViewModel.deleteMovie(currentMovie)
                Snackbar.make(requireView(), "Movie deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        homeViewModel.insertMovie(currentMovie)
                    }.show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
    }

    private fun prepareRecyclerView() {
        favoritesMoviesAdapter = FavoritesMoviesAdapter()
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favoritesMoviesAdapter
        }
    }

    private fun observeFavorites() {
        homeViewModel.observeFavoritesMoviesLiveData()
            .observe(viewLifecycleOwner) {
                favoritesMoviesAdapter.differ.submitList(it)
            }
    }
}