package com.example.tmdbborrador.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdbborrador.data.db.MovieDatabase

class HomeViewModelFactory(
    private val movieDatabase: MovieDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(movieDatabase) as T
    }
}