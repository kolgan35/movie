package com.github.movie.adapter

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.movie.R
import com.github.movie.data.models.MovieData
import com.github.movie.databinding.ItemMovieBinding
import com.github.movie.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate


class MovieListAdapterDelegate : AbsListItemAdapterDelegate<MovieData, MovieData, MovieListAdapterDelegate.Holder>(){


    override fun isForViewType(
        item: MovieData,
        items: MutableList<MovieData>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(ItemMovieBinding::inflate))
    }

    override fun onBindViewHolder(item: MovieData, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }


    class Holder(
        private val binding: ItemMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieData) {

            with(binding) {
                name.text = item.title
                movieType.text = item.type
                yearMovie.text = item.year
                itemMovie.animation =
                    AnimationUtils.loadAnimation(itemView.context, R.anim.rv_item_anim)
                Glide.with(itemView)
                    .load(item.poster)
                    .placeholder(R.drawable.ic_movie)
                    .into(posterMovie)
            }
        }
    }



}