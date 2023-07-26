package com.example.tmdbborrador.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbborrador.adapters.MovieAdapter
import com.example.tmdbborrador.databinding.FragmentHomeBinding
import com.example.tmdbborrador.ui.activities.MainActivity
import com.example.tmdbborrador.ui.activities.MovieActivity
import com.example.tmdbborrador.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var upcomingMovieAdapter: MovieAdapter
    private lateinit var topRatedMovieAdapter: MovieAdapter

    companion object {
        const val MOVIE_ID = "com.example.tmdbborrador.ui.fragments.idMovie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        homeViewModel = (activity as MainActivity).viewModel

        popularMovieAdapter = MovieAdapter()
        upcomingMovieAdapter = MovieAdapter()
        topRatedMovieAdapter = MovieAdapter()

        homeViewModel.getPopularMovies()
        homeViewModel.getUpcomingMovies()
        homeViewModel.getTopRatedMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(binding.rvTrending, popularMovieAdapter)
        setUpRecyclerView(binding.rvUpcoming, upcomingMovieAdapter)
        setUpRecyclerView(binding.rvTopRated, topRatedMovieAdapter)

        observeMovies()

        setMovieClick(popularMovieAdapter)
        setMovieClick(upcomingMovieAdapter)
        setMovieClick(topRatedMovieAdapter)
    }


    private fun setUpRecyclerView(recyclerView: RecyclerView, adapter: MovieAdapter) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
    }

    private fun observeMovies() {
        homeViewModel.observePopularLiveData().observe(viewLifecycleOwner) {
            popularMovieAdapter.differ.submitList(it)
        }
        homeViewModel.observeUpcomingLiveData().observe(viewLifecycleOwner) {
            upcomingMovieAdapter.differ.submitList(it)
        }
        homeViewModel.observeTopRatedLiveData().observe(viewLifecycleOwner) {
            topRatedMovieAdapter.differ.submitList(it)
        }
    }

    private fun setMovieClick(adapter: MovieAdapter){
        adapter.onItemClick = {
            val intent = Intent(activity, MovieActivity::class.java)
            intent.putExtra(MOVIE_ID, it.id)
            intent.putExtra("SOURCE", "HOME_FRAGMENT")
            startActivity(intent)
        }
    }

}