package com.example.ttyc.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.ttyc.data.api.MovieApiInterface
import com.example.ttyc.data.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : MovieApiInterface,
                              private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int,Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}