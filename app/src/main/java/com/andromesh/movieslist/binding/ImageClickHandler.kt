package com.andromesh.movieslist.binding

import android.view.View
import android.widget.ImageButton
import com.andromesh.movieslist.R
import com.andromesh.movieslist.movies.data.Movie
import com.andromesh.movieslist.movies.ui.MoviesViewModel

class ImageClickHandler {

    fun onImageButtonClicked(
        imageButton: View,
        movie: Movie,
        movieViewModel: MoviesViewModel?
    ) {


        movie.isFavorite = !movie.isFavorite
        movieViewModel?.updateMovie(movie)

        /*if (movie.isFavorite) {
            (imageButton as ImageButton).setImageResource(R.drawable.ic_favorite_filled)
        } else {
            (imageButton as ImageButton).setImageResource(R.drawable.ic_favorite_hollow)
        }*/
    }
}