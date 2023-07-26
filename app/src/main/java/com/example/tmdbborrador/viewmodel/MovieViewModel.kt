package com.example.tmdbborrador.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.tmdbborrador.data.db.MovieDatabase
import com.example.tmdbborrador.data.model.*
import com.example.tmdbborrador.data.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(
    private val movieDatabase: MovieDatabase
) : ViewModel() {

    private var movieDetailLiveData = MutableLiveData<MovieDetail>()
    private var movieVideoLiveData = MutableLiveData<MovieVideos>()
    private var castMemberLiveData = MutableLiveData<List<MovieCast>>()
    private var crewMemberLiveData = MutableLiveData<List<MovieCrew>>()

    fun getMovieDetail(id: Int) {
        RetrofitInstance.api.getMovieDetails(id.toString())
            .enqueue(object : Callback<MovieDetail> {
                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    if (response.isSuccessful) {
                        movieDetailLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }
            })
    }

    fun getMovieVideos(id: Int) {
        RetrofitInstance.api.getMovieVideos(id.toString())
            .enqueue(object : Callback<MovieVideosList> {
                override fun onResponse(
                    call: Call<MovieVideosList>,
                    response: Response<MovieVideosList>
                ) {
                    if (response.isSuccessful) {
                        val videoList = response.body()?.results
                        val officialTrailer = videoList?.find {
                            it.type == "Trailer" && it.official
                        }
                        movieVideoLiveData.value = officialTrailer!!
                    }
                }

                override fun onFailure(call: Call<MovieVideosList>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }
            })
    }

    fun getCastMembers(id: Int) {
        RetrofitInstance.api.getCastList(id.toString())
            .enqueue(object : Callback<CastList> {
                override fun onResponse(call: Call<CastList>, response: Response<CastList>) {
                    if (response.isSuccessful) {
                        castMemberLiveData.value = response.body()!!.cast
                        crewMemberLiveData.value = response.body()!!.crew
                    }
                }

                override fun onFailure(call: Call<CastList>, t: Throwable) {
                    Log.d("MovieViewModel", t.message.toString())
                }
            })
    }

    fun observeMovieDetailLiveData(): LiveData<MovieDetail> {
        return movieDetailLiveData
    }

    fun observeMovieVideoLiveData(): LiveData<MovieVideos> {
        return movieVideoLiveData
    }

    fun observeCastMemberLiveData(): LiveData<List<MovieCast>> {
        return castMemberLiveData
    }

    fun observeCrewMemberLiveData(): LiveData<List<MovieCrew>>{
        return crewMemberLiveData
    }

    fun insertMovie(movieDetail: MovieDetail) {
        viewModelScope.launch {
            movieDatabase.movieDetailDao().insertUpdateMovie(movieDetail)
        }
    }

}