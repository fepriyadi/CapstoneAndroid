package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponses(
     @field:SerializedName("page") 
    var page: Int? = null,
     @field:SerializedName("results") 
    var results: ArrayList<MovieResponse> = arrayListOf(),
     @field:SerializedName("total_pages") 
    var totalPages: Int? = null,
     @field:SerializedName("total_results") 
    var totalResults: Int? = null
)