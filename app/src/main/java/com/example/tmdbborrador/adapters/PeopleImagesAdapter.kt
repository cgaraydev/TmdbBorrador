package com.example.tmdbborrador.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbborrador.data.model.PeopleImageList
import com.example.tmdbborrador.data.model.Profile
import com.example.tmdbborrador.databinding.PeopleImageItemBinding

class PeopleImagesAdapter : RecyclerView.Adapter<PeopleImagesAdapter.PeopleImageViewHolder>() {

    inner class PeopleImageViewHolder(var binding: PeopleImageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Profile>() {
        override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem.file_path == newItem.file_path
        }

        override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleImageViewHolder {
        return PeopleImageViewHolder(
            PeopleImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: PeopleImageViewHolder, position: Int) {
        val people = differ.currentList[position]
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500/${people.file_path}")
            .into(holder.binding.ivPeopleDetailImages)
    }

}