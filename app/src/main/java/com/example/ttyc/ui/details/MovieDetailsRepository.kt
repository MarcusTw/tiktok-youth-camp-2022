package com.example.ttyc.ui.details

import androidx.lifecycle.LiveData
import com.example.ttyc.data.api.MovieApiInterface
import com.example.ttyc.data.repository.MovieNetworkDataSource
import com.example.ttyc.data.repository.NetworkState
import com.example.ttyc.data.models.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : MovieApiInterface) {

    private lateinit var movieDetailsNetworkDataSource: MovieNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.getMovieDetails(movieId)

        return movieDetailsNetworkDataSource.movieDetailsResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }



}