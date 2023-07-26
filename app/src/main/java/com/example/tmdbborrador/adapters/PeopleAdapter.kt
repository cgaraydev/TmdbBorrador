package com.example.tmdbborrador.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbborrador.data.model.MovieCast
import com.example.tmdbborrador.databinding.CastItemBinding

class PeopleAdapter : RecyclerView.Adapter<PeopleAdapter.CastViewHolder>() {

    lateinit var onItemClick:((MovieCast) -> Unit)

    inner class CastViewHolder(val binding: CastItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<MovieCast>() {
        override fun areItemsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieCast, newItem: MovieCast): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            CastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = differ.currentList[position]
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w185/${cast.profile_path}")
            .into(holder.binding.ivCast)
        holder.binding.tvCastCharacter.text = cast.character
        holder.binding.tvCastName.text = cast.name

        holder.itemView.setOnClickListener {
            onItemClick.invoke(cast)
        }
    }
}