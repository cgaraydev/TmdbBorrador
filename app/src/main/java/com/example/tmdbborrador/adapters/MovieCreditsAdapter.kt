package com.example.tmdbborrador.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbborrador.data.model.Movie
import com.example.tmdbborrador.data.model.PeopleCast
import com.example.tmdbborrador.databinding.MovieItemBinding

class MovieCreditsAdapter : RecyclerView.Adapter<MovieCreditsAdapter.MovieCreditsViewHolder>() {

    lateinit var onItemClick: ((PeopleCast) -> Unit)


    inner class MovieCreditsViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<PeopleCast>() {
        override fun areItemsTheSame(oldItem: PeopleCast, newItem: PeopleCast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PeopleCast, newItem: PeopleCast): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCreditsViewHolder {
        return MovieCreditsViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: MovieCreditsViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.binding.tvMovieNameItem.text = movie.title
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
            .into(holder.binding.ivMovieItem)
        holder.binding.tvYearMovieItem.text = movie.release_date.take(4)
        holder.binding.tvRatingItem.text = movie.vote_average.toString().take(3)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(movie)
        }
    }

}