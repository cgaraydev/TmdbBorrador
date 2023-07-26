package com.example.tmdbborrador.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbborrador.databinding.MovieItemBinding
import com.example.tmdbborrador.data.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    lateinit var onItemClick: ((Movie) -> Unit)

    private val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    inner class MovieViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w185/${movie.poster_path}")
            .into(holder.binding.ivMovieItem)
        holder.binding.tvMovieNameItem.text = movie.title
        holder.binding.tvYearMovieItem.text = movie.release_date.take(4)
        holder.binding.tvRatingItem.text = movie.vote_average.toString()

        holder.itemView.setOnClickListener {
            onItemClick.invoke(movie)
        }
    }
}