package com.example.ttyc.data.models


import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val id: Int,
    val overview: String,
    val runtime: Int,
    val title: String,
    val tagline: String,

    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val rating: Double
)