package com.example.tmdbborrador.data.retrofit

import com.example.tmdbborrador.data.model.*
import com.example.tmdbborrador.util.Constants.Companion.POPULAR_ENDPOINT
import com.example.tmdbborrador.util.Constants.Companion.UPCOMING_ENDPOINT
import com.example.tmdbborrador.util.Constants.Companion.TOP_RATED_ENDPOINT
import com.example.tmdbborrador.util.Constants.Companion.MOVIE_DETAILS_ENDPOINT
import com.example.tmdbborrador.util.Constants.Companion.MOVIE_VIDEOS_ENDPOINT
import com.example.tmdbborrador.util.Constants.Companion.MOVIE_CREDITS_ENDPOINT
import com.example.tmdbborrador.util.Constants.Companion.PEOPLE_DETAIL_ENDPOINT
import com.example.tmdbborrador.util.Constants.Companion.PEOPLE_IMAGE_ENDPOINT
import com.example.tmdbborrador.util.Constants.Companion.PEOPLE_MOVIE_CREDITS
import com.example.tmdbborrador.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET(POPULAR_ENDPOINT)
    fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieList>

    @GET(UPCOMING_ENDPOINT)
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieList>

    @GET(TOP_RATED_ENDPOINT)
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieList>

    @GET(MOVIE_DETAILS_ENDPOINT)
    fun getMovieDetails(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieDetail>

    @GET(MOVIE_VIDEOS_ENDPOINT)
    fun getMovieVideos(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieVideosList>

    @GET(MOVIE_CREDITS_ENDPOINT)
    fun getCastList(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<CastList>

    @GET(PEOPLE_DETAIL_ENDPOINT)
    fun getPeopleDetail(
        @Path("person_id") id: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<People>

    @GET(PEOPLE_IMAGE_ENDPOINT)
    fun getPeopleImages(
        @Path("person_id") id: String,
        @Query("api_key") apiKey: String = API_KEY
    ):Call<PeopleImageList>

    @GET(PEOPLE_MOVIE_CREDITS)
    fun getPeopleMovieCredits(
        @Path("person_id") id: String,
        @Query("api_key") apiKey: String = API_KEY
    ) : Call<PeopleMovieCreditsList>

}