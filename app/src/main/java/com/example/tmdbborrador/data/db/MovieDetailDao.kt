package com.example.tmdbborrador.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tmdbborrador.data.model.MovieDetail

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateMovie(movieDetail: MovieDetail)

    @Delete
    suspend fun deleteMovie(movieDetail: MovieDetail)

    @Query("SELECT * FROM fav_movies")
    fun getAllMovies(): LiveData<List<MovieDetail>>

}