package com.example.tmdbborrador.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.tmdbborrador.R
import com.example.tmdbborrador.data.db.MovieDatabase
import com.example.tmdbborrador.databinding.ActivityMainBinding
import com.example.tmdbborrador.viewmodel.HomeViewModel
import com.example.tmdbborrador.viewmodel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: HomeViewModel by lazy {
        val movieDatabase = MovieDatabase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(movieDatabase)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController = Navigation.findNavController(this, R.id.mainContainer)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}