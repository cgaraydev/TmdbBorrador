package com.example.tmdbborrador.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdbborrador.data.model.Movie
import com.example.tmdbborrador.data.model.MovieDetail

@Database(entities = [MovieDetail::class], version = 2)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDatabase :RoomDatabase(){
    abstract fun movieDetailDao(): MovieDetailDao

    companion object {
        @Volatile
        var INSTANCE: MovieDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, MovieDatabase::class.java, "movie_db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MovieDatabase
        }
    }
}