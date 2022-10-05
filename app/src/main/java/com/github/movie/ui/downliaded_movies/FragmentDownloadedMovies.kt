package com.github.movie.ui.downliaded_movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.movie.R
import com.github.movie.adapter.MovieListAdapter
import com.github.movie.databinding.FragmentDownloadedMoviesBinding
import com.github.movie.utils.movieConverter
import timber.log.Timber

class FragmentDownloadedMovies : Fragment(R.layout.fragment_downloaded_movies) {

    private val viewModel: DownloadedMoviesViewModel by viewModels()
    private var movieAdapter: MovieListAdapter by autoCleared()
    private val binding: FragmentDownloadedMoviesBinding by viewBinding(
        FragmentDownloadedMoviesBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        viewModel.movieLiveData.observe(viewLifecycleOwner) {
            movieAdapter.items = it.movieConverter()
        }

        Timber.d(movieAdapter.itemCount.toString())

    }

    private fun initList() {
        movieAdapter = MovieListAdapter()
        with(binding.rv) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
        }
    }
}