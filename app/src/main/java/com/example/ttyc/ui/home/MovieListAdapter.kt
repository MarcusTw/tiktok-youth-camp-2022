package com.example.ttyc.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ttyc.R
import com.example.ttyc.data.api.MovieApiService.Companion.POSTER_BASE_URL
import com.example.ttyc.data.repository.NetworkState
import com.example.ttyc.data.models.Movie
import com.example.ttyc.ui.details.MovieDetails
import kotlinx.android.synthetic.main.movie_list_item.view.*
import kotlinx.android.synthetic.main.network_state_item.view.*

const val MOVIES_LIST_VIEW = 1
const val NETWORK_STATE_VIEW = 2

class MovieListAdapter(private val context: Context)
    : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        return if (viewType == MOVIES_LIST_VIEW) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_STATE_VIEW
        } else {
            MOVIES_LIST_VIEW
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    class MovieItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie?,context: Context) {
            val releaseDate = if (movie?.releaseDate == null || movie.releaseDate == "") "2023" else movie.releaseDate
            itemView.movie_title.text = movie?.title
            itemView.movie_release_date.text =  releaseDate
            itemView.movie_rating.text = movie?.rating.toString()
            val moviePosterURL = POSTER_BASE_URL + movie?.posterPath

            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.movie_poster);

            itemView.setOnClickListener{
                val intent = Intent(context, MovieDetails::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }
        }
    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE;
            }
            else  {
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = R.string.network_error.toString();
            }
            else if (networkState != null && networkState == NetworkState.END_OF_LIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = R.string.end_of_list.toString();
            }
            else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}