package com.example.tmdbborrador.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdbborrador.data.db.MovieDatabase

class MovieViewModelFactory(
    private val movieDatabase: MovieDatabase
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(movieDatabase) as T
    }
}