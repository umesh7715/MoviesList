package com.andromesh.movieslist.movies.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MoviePagedDataSourceFactory @Inject constructor(
    private val searchText: String,
    private val dataSource: MovieRemoteDataSource,
    private val dao: MovieDao,
    private val scope: CoroutineScope) : DataSource.Factory<Int, Movie>() {

    private val liveData = MutableLiveData<MoviePagedDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = MoviePagedDataSource(searchText, dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 100

        fun pagedListConfig() = PagedList.Config.Builder()
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(true)
                .build()
    }

}