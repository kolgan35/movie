package com.github.movie.ui.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.github.movie.R
import com.github.movie.databinding.DetailFragmentBinding
import com.github.movie.utils.toast
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val binding: DetailFragmentBinding by viewBinding(DetailFragmentBinding::bind)
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        viewModel.loadPoster(this, args.posterUri, binding.image)
        bindInfo(id)
        observeLiveData()
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            duration = CARD_DURATION
            scrimColor = Color.TRANSPARENT
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindInfo(id: String) {
        lifecycleScope.launch {
            try {
                viewModel.getInfo(id).apply {
                    binding.setMovieTitle(this.title)
                    binding.setGenre(this.genre)
                    binding.setYear(this.year)
                    binding.setRuntime(this.runtime)
                    binding.setRated(this.rated)
                    val ratingString = this.rating.joinToString(
                        separator = ",\n",
                        transform = { "${it.source}: ${it.value}" }
                    )
                    binding.setRating(ratingString)
                    binding.setPlot(this.plot)
                }
            } catch (t: Throwable) {
                toast("Отсутствует интернет-соединение")
            }
        }
    }

    private fun observeLiveData() {
        viewModel.toastLiveEvent.observe(viewLifecycleOwner) {
            toast(it)
        }
    }

    companion object {
        private const val CARD_DURATION = 500L
    }

}