package com.example.tmdbborrador.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbborrador.adapters.PeopleAdapter
import com.example.tmdbborrador.data.db.MovieDatabase
import com.example.tmdbborrador.data.model.MovieDetail
import com.example.tmdbborrador.data.model.MovieVideos
import com.example.tmdbborrador.databinding.ActivityMovieBinding
import com.example.tmdbborrador.ui.fragments.HomeFragment
import com.example.tmdbborrador.viewmodel.MovieViewModel
import com.example.tmdbborrador.viewmodel.MovieViewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var youTubePlayer: YouTubePlayer
    private lateinit var castAdapter: PeopleAdapter
    private var movieToSave: MovieDetail? = null
    private var movieId: Int = 0
    private var source: String? = null

    companion object {
        const val PEOPLE_ID = "com.example.tmdbborrador.ui.fragments.idPeople"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieDatabase = MovieDatabase.getInstance(this)
        val viewModelFactory = MovieViewModelFactory(movieDatabase)
        movieViewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]

//        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        getSourceFromIntent()
        getMovieId()

        castAdapter = PeopleAdapter()

        movieViewModel.getMovieDetail(movieId)
        observeMovieDetail()

        movieViewModel.getMovieVideos(movieId)
        observeVideos()

        movieViewModel.getCastMembers(movieId)
        setUpRecyclerView(binding.rvCast, castAdapter)

        observeCast()

        onFavouriteClick()
        setPeopleClick(castAdapter)

    }

    private fun getSourceFromIntent() {
        val bundle: Bundle? = intent.extras
        source = bundle?.getString("SOURCE")
    }

    private fun getMovieId() {
        val bundle: Bundle? = intent.extras
        when (source) {
            "HOME_FRAGMENT" -> {
                movieId = bundle?.getInt(HomeFragment.MOVIE_ID) ?: 0
            }
            "PEOPLE_ACTIVITY" -> {
                movieId = bundle?.getInt(PeopleActivity.MOVIE_CREDITS_ID) ?: 0
            }
        }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView, adapter: PeopleAdapter) {
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@MovieActivity, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
    }

    private fun observeCast() {
        movieViewModel.observeCastMemberLiveData().observe(this) {
            castAdapter.differ.submitList(it)
        }
    }

    private fun observeVideos(){
        movieViewModel.observeMovieVideoLiveData().observe(this) {
            setYoutubeVideo(it)
        }
    }

    private fun observeMovieDetail(){
        movieViewModel.observeMovieDetailLiveData().observe(this) {
            setMovieDetailView(it)
        }
    }

    private fun setYoutubeVideo(movieVideos: MovieVideos) {
        val youTubePlayerView = binding.youtubePlayer
        val iFramePlayerOptions =
            IFramePlayerOptions.Builder().controls(1).mute(1).build()
        val videoId = movieVideos.key
        youTubePlayerView.enableAutomaticInitialization = false
        youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@MovieActivity.youTubePlayer = youTubePlayer
                youTubePlayer.loadVideo(videoId, 0f)
            }
        }, iFramePlayerOptions)
        lifecycle.addObserver(youTubePlayerView)
    }

    @SuppressLint("SetTextI18n")
    private fun setMovieDetailView(movieDetail: MovieDetail) {
        with(binding) {
            tvMovieDetailTitle.text = movieDetail.title
            tvYearDetail.text = movieDetail.release_date.take(4)
            tvRuntimeDetail.text = "${movieDetail.runtime} min"
            Glide.with(this@MovieActivity)
                .load("https://image.tmdb.org/t/p/w500/${movieDetail.poster_path}")
                .into(ivPosterDetail)
            tvOverviewDetail.text = movieDetail.overview
        }
        movieToSave = movieDetail
    }

    private fun setCrewDetail(){

    }

    private fun onFavouriteClick() {
        binding.btnAddFav.setOnClickListener {
            movieToSave?.let {
                movieViewModel.insertMovie(it)
                Toast.makeText(this, "Movie saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setPeopleClick(adapter: PeopleAdapter) {
        adapter.onItemClick = {
            val intent = Intent(this, PeopleActivity::class.java)
            intent.putExtra(PEOPLE_ID, it.id)
            startActivity(intent)
        }
    }
}

