package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: Collection?,

    @SerializedName("budget")
    val budget: Int,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("status")
    val status: String,

    @SerializedName("tagline")
    val tagline: String?,

    @SerializedName("title")
    val title: String,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("videos")
    val videos: VideoResponse?,

    @SerializedName("credits")
    val credits: Credits?,
){

    val posterURl: String
        get(){return "https://image.tmdb.org/t/p/w500${posterPath}"}

    val backdropURl: String
        get(){return "https://image.tmdb.org/t/p/w500${backdropPath}"}

    var isFavoriteMovie: Boolean = false

}


data class Collection(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?
)

data class Genre(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)

data class VideoResponse(
    @SerializedName("results")
    val results: List<Video>
)

data class Video(
    @SerializedName("id")
    val id: String,

    @SerializedName("iso_639_1")
    val iso6391: String,

    @SerializedName("iso_3166_1")
    val iso31661: String,

    @SerializedName("key")
    val key: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("site")
    val site: String,

    @SerializedName("size")
    val size: Int,

    @SerializedName("type")
    val type: String
)

data class Credits(
    @SerializedName("cast")
    val cast: List<Cast>,

    @SerializedName("crew")
    val crew: List<Crew>
)

data class Cast(
    @SerializedName("cast_id")
    val castId: Int,

    @SerializedName("character")
    val character: String,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("order")
    val order: Int,

    @SerializedName("profile_path")
    val profilePath: String?
){
    val profileURl: String
        get(){return "https://image.tmdb.org/t/p/w500${profilePath}"}
}

data class Crew(
    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("department")
    val department: String,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("profile_path")
    val profilePath: String?,

    @SerializedName("job")
    val job: String
)