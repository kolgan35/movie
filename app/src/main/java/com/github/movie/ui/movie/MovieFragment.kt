package com.github.movie.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.movie.R
import com.github.movie.adapter.MoviePagingAdapter
import com.github.movie.domain.models.MovieData
import com.github.movie.domain.models.MovieType
import com.github.movie.databinding.MoviewFragmentBinding
import com.github.movie.utils.getMovieCheckedType
import com.github.movie.utils.textChangedFlow
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.moview_fragment), MoviePagingAdapter.OnItemClickListenr {

    private val binding: MoviewFragmentBinding by viewBinding(MoviewFragmentBinding::bind)
    private val viewModel by viewModels<MovieViewModel>()
    private val pagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MoviePagingAdapter(requireContext(), this)
    }
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        Timber.d(pagingAdapter.itemCount.toString())
        search()
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        binding.upButton.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun initList() {
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


    override fun onItemClick(view: View, movies: MovieData) {
        val movieTransitionName = getString(R.string.detail_transition_name)
        val extras = FragmentNavigatorExtras(view to movieTransitionName)
        findNavController().navigate(
            MovieFragmentDirections.actionFragmentMoviesToDetailFragment(
                movies.id,
                movies.poster
            ),
            extras
        )

        exitTransition = MaterialElevationScale(false).apply {
            duration = CARD_DURATION
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = CARD_DURATION
        }
    }

    companion object {
        private const val CARD_DURATION = 500L
    }
}