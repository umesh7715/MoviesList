package com.andromesh.movieslist.movies.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.andromesh.movieslist.api.BaseDataSource
import com.andromesh.movieslist.database.resultLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val movieRemoteDataSource: MovieRemoteDataSource
) : BaseDataSource() {

    fun observerPagedMovies(
        connectivityAvailable: Boolean, searchText: String,
        coroutineScope: CoroutineScope
    ) =
        if (connectivityAvailable) observeRemotePagedSets(searchText, coroutineScope)
        else observeLocalPagedSets(searchText)

    private fun observeLocalPagedSets(searchText: String): LiveData<PagedList<Movie>> {

        val dataSourceFactory = if (searchText.isEmpty())
            movieDao.getPagedMovies()
        else
            movieDao.getSearchedPagedMovies("%$searchText%")

        return LivePagedListBuilder(
            dataSourceFactory,
            MoviePagedDataSourceFactory.pagedListConfig()
        ).build()
    }

    private fun observeRemotePagedSets(searchText: String, ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<Movie>> {
        val dataSourceFactory = MoviePagedDataSourceFactory(
            searchText, movieRemoteDataSource,
            movieDao, ioCoroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            MoviePagedDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observeMovieDetals(id: Int) = resultLiveData(
        databaseQuery = { movieDao.getMovieById(id) },
        networkCall = { movieRemoteDataSource.getMovieDetails(id) },
        saveCallResult = { movieDao.insertMovieDetails(it) }
    ).distinctUntilChanged()

    fun updateMovie(
        movie: Movie,
        ioCoroutineScope: CoroutineScope
    ) {
        ioCoroutineScope.launch {
            movieDao.updateMovie(movie)
        }
    }

    fun getFavorites() = liveData(Dispatchers.IO) {
        val id = movieDao.getFavoriteMovies()
        emit(id)
    }


}