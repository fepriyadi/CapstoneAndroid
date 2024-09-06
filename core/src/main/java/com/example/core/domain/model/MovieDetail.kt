package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val runtime: Int?,
    val rating: String,
    val posterPath: String?,
    val backdropPath: String?,
    val videos: List<VideoDomain>,
    val credits: CreditsDomain,
    val genres: List<GenreDomain>,
    var isFavoriteMovie: Boolean = false
)

 : Parcelable{

    val posterURl: String
        get(){return "https://image.tmdb.org/t/p/w500${posterPath}"}

    val backdropURl: String
        get(){return "https://image.tmdb.org/t/p/w500${backdropPath}"}

    val genreName: String get() {
        return if (genres.isNotEmpty()) genres.first().name else ""
    }
}

@Parcelize
data class VideoDomain(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
) : Parcelable

@Parcelize
data class CreditsDomain(
    val cast: List<CastDomain>,
    val crew: List<CrewDomain>
) : Parcelable

@Parcelize
data class CastDomain(
    val id: Int,
    val character: String,
    val name: String,
    val profilePath: String?
) : Parcelable{

    val profileUrl: String
        get(){return "https://image.tmdb.org/t/p/w500${profilePath}"}
}

@Parcelize
data class CrewDomain(
    val id: Int,
    val job: String,
    val name: String,
    val profilePath: String?
) : Parcelable

@Parcelize
data class GenreDomain(
    val id: Int,
    val name: String
) : Parcelable




