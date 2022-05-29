package com.example.ttyc.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ttyc.data.api.MovieApiInterface
import com.example.ttyc.data.models.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieNetworkDataSource (
        private val apiService : MovieApiInterface,
        private val compositeDisposable: CompositeDisposable
    ) {

    private val networkStateSingleton  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = networkStateSingleton                   //with this get, no need to implement get function to get networkSate

    private val movieDetailsResponseSingleton =  MutableLiveData<MovieDetails>()
    val movieDetailsResponse: LiveData<MovieDetails>
        get() = movieDetailsResponseSingleton

    fun getMovieDetails(movieId: Int) {
        networkStateSingleton.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            movieDetailsResponseSingleton.postValue(it)
                            networkStateSingleton.postValue(NetworkState.LOADED)
                        },
                        {
                            networkStateSingleton.postValue(NetworkState.ERROR)
                        }
                    )
            )
        }
        catch (e: Exception){
            Log.e("MovieDetailsDataSource", e.message.toString())
        }
    }
}