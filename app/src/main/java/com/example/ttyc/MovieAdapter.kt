package com.example.ttyc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ttyc.models.Movie
import kotlinx.android.synthetic.main.activity_item.view.*

class MovieAdapter(private val movies: List<Movie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

   class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

       fun bind(movie: Movie)  {
           itemView.movie_title.text = movie.title
           itemView.movie_vote_average.text = movie.vote_average.toString()
           itemView.movie_release_date.text = movie.release
           Glide.with(itemView).load("https://image.tmdb.org/t/p/w500/" + movie.poster_path).into(itemView.movie_poster)
       }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, index: Int) {
        holder.bind(movies[index])
    }
}