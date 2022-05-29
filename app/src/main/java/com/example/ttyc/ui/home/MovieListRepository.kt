package com.example.ttyc.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.ttyc.data.api.MovieApiService.LIMIT
import com.example.ttyc.data.api.MovieApiInterface
import com.example.ttyc.data.repository.MovieDataSource
import com.example.ttyc.data.repository.MovieDataSourceFactory
import com.example.ttyc.data.repository.NetworkState
import com.example.ttyc.data.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieListRepository (private val apiService : MovieApiInterface) {

    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(LIMIT)
            .build()
        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}