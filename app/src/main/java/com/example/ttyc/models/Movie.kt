package com.example.ttyc.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id : String?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("overview")
    val overview : String?,

    @SerializedName("poster_path")
    val poster_path : String?,

    @SerializedName("release_date")
    val release : String?,

    @SerializedName("vote_average")
    val vote_average : Number?

) : Parcelable {
    constructor() : this("", "", "", "", "", 0)
}