package com.github.movie.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.movie.R
import com.github.movie.databinding.DetailFragmentBinding

class DetailFragment: Fragment(R.layout.detail_fragment) {

    private val binding: DetailFragmentBinding by viewBinding(DetailFragmentBinding::bind)
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = args.title

    }

}