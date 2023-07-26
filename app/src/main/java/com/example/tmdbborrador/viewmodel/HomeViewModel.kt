package com.example.tmdbborrador.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbborrador.data.db.MovieDatabase
import com.example.tmdbborrador.data.model.Movie
import com.example.tmdbborrador.data.model.MovieDetail
import com.example.tmdbborrador.data.model.MovieList
import com.example.tmdbborrador.data.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val movieDatabase: MovieDatabase
) : ViewModel() {

    private var popularMoviesLiveData = MutableLiveData<List<Movie>>()
    private var upcomingMoviesLiveData = MutableLiveData<List<Movie>>()
    private var topRatedMoviesLiveData = MutableLiveData<List<Movie>>()
    private var favoritesMoviesLiveData = movieDatabase.movieDetailDao().getAllMovies()


    fun getPopularMovies() {
        RetrofitInstance.api.getPopularMovies()
            .enqueue(object : Callback<MovieList> {
                override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                    if (response.body() != null) {
                        popularMoviesLiveData.value = response.body()!!.results
                    }
                }

                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }
            })
    }

    fun getUpcomingMovies() {
        RetrofitInstance.api.getUpcomingMovies().enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.body() != null) {
                    upcomingMoviesLiveData.value = response.body()!!.results
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getTopRatedMovies() {
        RetrofitInstance.api.getTopRatedMovies().enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.body() != null) {
                    topRatedMoviesLiveData.value = response.body()!!.results
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun insertMovie(movieDetail: MovieDetail){
        viewModelScope.launch {
            movieDatabase.movieDetailDao().insertUpdateMovie(movieDetail)
        }
    }

    fun deleteMovie(movieDetail: MovieDetail){
        viewModelScope.launch {
            movieDatabase.movieDetailDao().deleteMovie(movieDetail)
        }
    }

    fun observePopularLiveData(): LiveData<List<Movie>> {
        return popularMoviesLiveData
    }

    fun observeUpcomingLiveData(): LiveData<List<Movie>> {
        return upcomingMoviesLiveData
    }

    fun observeTopRatedLiveData(): LiveData<List<Movie>> {
        return topRatedMoviesLiveData
    }

    fun observeFavoritesMoviesLiveData(): LiveData<List<MovieDetail>>{
        return favoritesMoviesLiveData
    }

}