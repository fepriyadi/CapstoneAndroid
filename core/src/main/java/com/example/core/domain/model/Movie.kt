package com.example.core.domain.model

import android.os.Parcelable
import com.example.core.utils.log
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

    private val imageUrl = "https://image.tmdb.org/t/p/w500"

    val posterURl: String
        get(){
            toString().log("poster is null ${posterPath == null}")
            return "$imageUrl$posterPath"
        }

    val backdropURl: String
        get(){ return "$imageUrl$backdropPath" }

}


