package com.example.tmdbborrador.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbborrador.data.model.MovieDetail
import com.example.tmdbborrador.databinding.FavItemBinding

class FavoritesMoviesAdapter :
    RecyclerView.Adapter<FavoritesMoviesAdapter.FavoritesMoviesViewHolder>() {

    inner class FavoritesMoviesViewHolder(val binding: FavItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<MovieDetail>() {
        override fun areItemsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMoviesViewHolder {
        return FavoritesMoviesViewHolder(
            FavItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoritesMoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w185/${movie.poster_path}").into(holder.binding.ivFavItem)
        holder.binding.tvFavItemTitle.text = movie.title
        holder.binding.tvFavItemYear.text = movie.release_date.take(4)
        holder.binding.tvFavItemDuration.text = "${movie.runtime} min"
    }
}