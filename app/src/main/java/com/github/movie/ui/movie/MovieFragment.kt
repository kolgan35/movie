package com.github.movie.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import autoCleared
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.movie.R
import com.github.movie.adapter.MoviePagingAdapter
import com.github.movie.data.models.MovieData
import com.github.movie.data.models.MovieType
import com.github.movie.databinding.MoviewFragmentBinding
import com.github.movie.utils.getMovieCheckedType
import com.github.movie.utils.textChangedFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieFragment : Fragment(R.layout.moview_fragment), MoviePagingAdapter.OnItemClickListenr {

    private val binding: MoviewFragmentBinding by viewBinding(MoviewFragmentBinding::bind)
    private val viewModel: MovieViewModel by viewModels()
    private var pagingAdapter: MoviePagingAdapter by autoCleared()
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        Timber.d(pagingAdapter.itemCount.toString())
//        bindInfo()
        search()
        Timber.tag("onViewCreated").d(pagingAdapter.itemCount.toString())
    }

    private fun initList() {
        pagingAdapter = MoviePagingAdapter(requireContext(), this)
        with(binding.recyclerView) {
            adapter = pagingAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
        }
    }

    private fun search() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            combine(
                binding.movieNameET.editText?.textChangedFlow()!!.onStart { emit("") },
                checkButtonChoice().onStart { emit(MovieType.MOVIE.name) }
            ) { text, type ->
                text to type
            }
                .debounce(500)
                .distinctUntilChanged()
                .mapLatest { (text, type) ->
                    if (text.isNotEmpty()) {
                        viewModel.searchMovies(text, type).observe(viewLifecycleOwner) {
                            pagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                        }
                    }
                }
                .collect()
        }
    }

    private fun checkButtonChoice(): Flow<String> {
        return binding.radioGroup.getMovieCheckedType().flowOn(Dispatchers.IO)
    }


    override fun onItemClick(movies: MovieData) {
        findNavController().navigate(
            MovieFragmentDirections.actionFragmentMoviesToDetailFragment(
                movies.title
            )
        )
    }
}