package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.data.source.remote.response.MovieResponses
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("api_key") apiKey: String): MovieResponses

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("api_key") apiKey: String): MovieResponses

    @GET("movie/popular")
    suspend fun getPopular(@Query("api_key") apiKey: String): MovieResponses

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("api_key") apiKey: String): MovieResponses

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int, @Query("api_key") apiKey: String,
        @Query("append_to_response") append: String? = "videos,credits"): MovieDetailResponse

    @GET("search/movie?")
    suspend fun searchMovie(@Query("api_key") apiKey: String, @Query("query") query: String,  @Query("page") page: Int = 1) : MovieResponses
}