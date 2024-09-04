package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int?,
    val title: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val overview: String?,
    val genre: String?,
    val year: String?,
    val duration: String?,
    val rating: String,
    val isFavorite: Boolean?
) : Parcelable{

    val posterURl: String
        get(){return "https://image.tmdb.org/t/p/w500${posterPath}"}

    val backdropURl: String
        get(){return "https://image.tmdb.org/t/p/w500${backdropPath}"}

}


