package com.example.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = 0,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String?,

    @ColumnInfo(name = "posterPath")
    var posterPath: String?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "genre")
    var genre: String,

    @ColumnInfo(name = "year")
    var year: String,

    @ColumnInfo(name = "duration")
    var duration: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean?,

    @ColumnInfo(name = "rating")
    var rating: String

)
