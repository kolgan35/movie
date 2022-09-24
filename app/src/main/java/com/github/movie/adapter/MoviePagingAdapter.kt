package com.github.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.github.movie.R
import com.github.movie.data.models.MovieData
import com.github.movie.databinding.ItemMovieBinding

class MoviePagingAdapter(context: Context, private val listener: OnItemClickListenr) :
    PagingDataAdapter<MovieData, MoviePagingAdapter.MoviePagingViewHolder>(MovieDiffItemCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagingViewHolder {
        return MoviePagingViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MoviePagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class MoviePagingViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding by viewBinding(ItemMovieBinding::bind)

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }

                }
            }
        }

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

    interface OnItemClickListenr {
        fun onItemClick(movies: MovieData)
    }

    private object MovieDiffItemCallback : DiffUtil.ItemCallback<MovieData>() {

        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            return oldItem.id == newItem.id
        }
    }
}



