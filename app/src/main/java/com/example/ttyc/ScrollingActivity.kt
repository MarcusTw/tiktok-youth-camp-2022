package com.example.ttyc

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttyc.models.Movie
import com.example.ttyc.models.MovieAPIResponse
import com.example.ttyc.services.MovieAPIInterface
import com.example.ttyc.services.MovieAPIService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScrollingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

        rv_movies_list.layoutManager = LinearLayoutManager(this)
        rv_movies_list.setHasFixedSize(true)
        getMovieList { movies: List<Movie> -> rv_movies_list.adapter = MovieAdapter(movies) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getMovieList(callback: (List<Movie>) -> Unit) {
        val apiService = MovieAPIService.getInstance().create(MovieAPIInterface::class.java)
        val apiKey = BuildConfig.ApiKey
        apiService.getMovies(apiKey).enqueue(object: Callback<MovieAPIResponse> {
            override fun onResponse(call: Call<MovieAPIResponse>, res: Response<MovieAPIResponse>) {
                return callback(res.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieAPIResponse>, t: Throwable) {
                print("Error in calling API")
                throw t
            }
        })
    }
}