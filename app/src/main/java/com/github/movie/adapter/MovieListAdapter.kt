package com.github.movie.adapter

import androidx.recyclerview.widget.DiffUtil
import com.github.movie.domain.models.MovieData
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class MovieListAdapter: AsyncListDifferDelegationAdapter<MovieData>(ContactDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MovieListAdapterDelegate())
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ContactDiffUtilCallback : DiffUtil.ItemCallback<MovieData>() {

        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem == newItem
        }
    }

}