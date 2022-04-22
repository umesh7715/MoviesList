package com.andromesh.movieslist.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.andromesh.movieslist.MainActivity
import com.andromesh.movieslist.R
import com.andromesh.movieslist.api.Result
import com.andromesh.movieslist.databinding.MovieFragmentBinding
import com.andromesh.movieslist.movies.data.Movie
import com.andromesh.movieslist.ui_utils.hide
import com.andromesh.movieslist.ui_utils.show
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()


    private lateinit var id: Number

    private lateinit var binding: MovieFragmentBinding
    private lateinit var imageView: ImageView

    override fun onResume() {
        super.onResume()
        imageView.visibility = View.VISIBLE
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        id = arguments?.let { MovieFragmentArgs.fromBundle(it).id }!!

        var applayout =
            (activity as MainActivity).findViewById(R.id.appbar) as CollapsingToolbarLayout
        applayout.title = arguments?.let { MovieFragmentArgs.fromBundle(it).name }


        imageView = (activity as MainActivity).findViewById(R.id.ivParallxedImage) as ImageView

        movieViewModel.id = id as Int

        binding = MovieFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root

        movieViewModel.movie.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    binding.movieDetails = result.data

                    val url = result.data?.poster_path?.let { Movie.getPath(it) }

                    Glide.with(imageView.context)
                        .load(url)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)

                }
                Result.Status.LOADING -> binding.progressBar.show()
                Result.Status.ERROR -> {
                    binding.progressBar.hide()
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

}

