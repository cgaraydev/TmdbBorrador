package com.example.tmdbborrador.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tmdbborrador.R
import com.example.tmdbborrador.adapters.MovieAdapter
import com.example.tmdbborrador.adapters.MovieCreditsAdapter
import com.example.tmdbborrador.adapters.PeopleAdapter
import com.example.tmdbborrador.adapters.PeopleImagesAdapter
import com.example.tmdbborrador.data.model.People
import com.example.tmdbborrador.data.model.PeopleMovieCreditsList
import com.example.tmdbborrador.databinding.ActivityPeopleBinding
import com.example.tmdbborrador.ui.fragments.HomeFragment
import com.example.tmdbborrador.viewmodel.PeopleViewModel

class PeopleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPeopleBinding
    private lateinit var peopleViewModel: PeopleViewModel
    private lateinit var peopleAdapter: PeopleAdapter
    private lateinit var peopleImagesAdapter: PeopleImagesAdapter
    private lateinit var movieCreditsAdapter: MovieCreditsAdapter
    private var peopleId: Int = 0

    companion object {
        const val MOVIE_CREDITS_ID = "com.example.tmdbborrador.ui.activities.idMovie"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeopleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        peopleViewModel = ViewModelProvider(this)[PeopleViewModel::class.java]

        getPeopleId()
        peopleImagesAdapter = PeopleImagesAdapter()
        movieCreditsAdapter = MovieCreditsAdapter()

        peopleViewModel.getPeopleDetail(peopleId)
        peopleViewModel.observePeopleDetailLiveData().observe(this) {
            setPeopleDetail(it)
        }

        peopleViewModel.getPeopleImages(peopleId)
        setUpRecyclerViewImages(peopleImagesAdapter)
        peopleViewModel.observePeopleImageLiveData().observe(this) {
            peopleImagesAdapter.differ.submitList(it.profiles)
        }

        peopleViewModel.getPeopleMovieCredits(peopleId)
        setUpRecyclerViewMovieCredits(movieCreditsAdapter)
        peopleViewModel.observePeopleMovieCreditsLiveData().observe(this) {
            movieCreditsAdapter.differ.submitList(it.cast)
        }
        setMovieCreditsClick(movieCreditsAdapter)
    }

    private fun setPeopleDetail(people: People) {
        with(binding) {
            tvBiography.text = people.biography
            tvBiography.setOnClickListener {
                if (tvBiography.maxLines == 4) {
                    tvBiography.maxLines = Int.MAX_VALUE
                    tvBiography.ellipsize = null
                } else {
                    tvBiography.maxLines = 4
                    tvBiography.ellipsize = TextUtils.TruncateAt.END
                }
            }
            Glide.with(this@PeopleActivity)
                .load("https://image.tmdb.org/t/p/w500/${people.profile_path}")
                .into(binding.ivPeopleDetail)
            tvPeopleNameDetail.text = people.name
            tvDateOfBirth.text = people.birthday
            tvPlaceOfBirth.text = people.place_of_birth
        }
    }

    private fun getPeopleId() {
        val bundle: Bundle? = intent.extras
        peopleId = bundle!!.getInt(MovieActivity.PEOPLE_ID)
    }

    private fun setUpRecyclerViewImages(adapter: PeopleImagesAdapter) {
        binding.rvPeopleImages.apply {
            layoutManager =
                LinearLayoutManager(this@PeopleActivity, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
    }

    private fun setUpRecyclerViewMovieCredits(adapter: MovieCreditsAdapter) {
        binding.rvPeopleMovieCredits.apply {
            layoutManager =
                LinearLayoutManager(this@PeopleActivity, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }
    }

    private fun setMovieCreditsClick(adapter: MovieCreditsAdapter) {
        adapter.onItemClick = {
            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra(MOVIE_CREDITS_ID, it.id)
            intent.putExtra("SOURCE", "PEOPLE_ACTIVITY")
            startActivity(intent)
        }
    }

}