package com.example.ttyc.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.ttyc.R
import com.example.ttyc.data.api.MovieApiService
import com.example.ttyc.data.api.MovieApiInterface
import com.example.ttyc.data.api.MovieApiService.POSTER_BASE_URL
import com.example.ttyc.data.repository.NetworkState
import com.example.ttyc.data.models.MovieDetails
import kotlinx.android.synthetic.main.movie_details.*

class MovieDetails : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieRepository: MovieDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : MovieApiInterface = MovieApiService.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bind(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

    }

    private fun bind(it: MovieDetails){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_overview.text = it.overview
        movie_release_date.text = String.format(" %s", it.releaseDate)
        movie_rating.text = String.format(" %s/10", it.rating.toString())
        movie_runtime.text = String.format(" %s minutes", it.runtime.toString())

        val moviePosterURL = POSTER_BASE_URL + it.posterPath

        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster)
    }


    private fun getViewModel(movieId: Int): MovieDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieRepository, movieId) as T
            }
        })[MovieDetailsViewModel::class.java]
    }
}
