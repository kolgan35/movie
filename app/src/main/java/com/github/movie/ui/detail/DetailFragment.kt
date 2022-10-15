package com.github.movie.ui.detail

import android.annotation.SuppressLint
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
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val binding: DetailFragmentBinding by viewBinding(DetailFragmentBinding::bind)
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        bindInfo(id)
        observeLiveData()
    }

    @SuppressLint("SetTextI18n")
    private fun bindInfo(id: String) {

        lifecycleScope.launch {
            try {
                viewModel.getInfo(id).apply {
                    binding.movieTitle.text = this.title
                    binding.genre.text = this.genre
                    binding.year.text = this.year
                    binding.runtime.text = this.runtime
                    binding.rated.text = this.rated
                    val ratingString = this.rating.joinToString(
                        separator = ",\n",
                        transform = { "${it.source}: ${it.value}" }
                    )

                    binding.rating.text = ratingString

                    binding.plot.text = this.plot

                    Glide.with(this@DetailFragment)
                        .load(this.poster)
                        .placeholder(R.drawable.ic_movie)
                        .into(binding.image)

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

}