package com.example.ttyc.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ttyc.data.repository.NetworkState
import com.example.ttyc.data.models.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel (private val movieRepository : MovieDetailsRepository, movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}