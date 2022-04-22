package com.andromesh.movieslist.movies.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.andromesh.movieslist.di.CoroutineScropeSupervisor
import com.andromesh.movieslist.movies.data.Movie
import com.andromesh.movieslist.movies.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    @CoroutineScropeSupervisor private val ioCoroutineScope: CoroutineScope
) : ViewModel() {


    var connectivityAvailable: Boolean = false


    var searchFilterText = MutableLiveData("")

    val moviesList = Transformations.switchMap(searchFilterText) { input ->
        movieRepository.observerPagedMovies(
            connectivityAvailable, input, ioCoroutineScope
        )
    }


    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }

    fun updateMovie(movie: Movie) {
        movieRepository.updateMovie(movie, ioCoroutineScope)
    }

    var favoriteMovies = movieRepository.getFavorites()


}
