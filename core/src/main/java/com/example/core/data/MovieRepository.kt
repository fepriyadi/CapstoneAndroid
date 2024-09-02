package com.example.core.data

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.MovieDetailResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.IMovieRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import com.example.core.utils.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getNowPlayingMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            val apiResponse: ApiResponse<List<MovieResponse>> = remoteDataSource.getNowPlayingMovie().first()
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    toString().log("getnowplaying success ${data.size}")
                    data.map {
                        emit(Resource.Success(DataMapper.mapResponseToModel(data)))
                    }
                }

                is ApiResponse.Empty -> {
                    toString().log("getnowplaying empty")
                }

                is ApiResponse.Error -> {
                    toString().log("getnowplaying error")
                    emit(
                        Resource.Error(apiResponse.errorMessage)
                    )
                }
            }
        }
    }

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            val apiResponse: ApiResponse<List<MovieResponse>> = remoteDataSource.getPopularMovie().first()
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    data.map {
                        emit(Resource.Success(DataMapper.mapResponseToModel(data)))
                    }
                }

                is ApiResponse.Empty -> {
                }

                is ApiResponse.Error -> {
                    emit(
                        Resource.Error(apiResponse.errorMessage)
                    )
                }
            }
        }
    }

    override fun getTopratedMovies(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            val apiResponse: ApiResponse<List<MovieResponse>> = remoteDataSource.getTopratedMovie().first()
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    data.map {
                        emit(Resource.Success(DataMapper.mapResponseToModel(data)))
                    }
                }

                is ApiResponse.Empty -> {
                }

                is ApiResponse.Error -> {
                    emit(
                        Resource.Error(apiResponse.errorMessage)
                    )
                }
            }
        }
    }

    override fun searchMovie(query: String): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            val apiResponse: ApiResponse<List<MovieResponse>> = remoteDataSource.searchMovie(query).first()
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    data.map {
                        emit(Resource.Success(DataMapper.mapResponseToModel(data)))
                    }
                }

                is ApiResponse.Empty -> {
                }

                is ApiResponse.Error -> {
                    emit(
                        Resource.Error(apiResponse.errorMessage)
                    )
                }
            }
        }
    }

    override fun getFavoriteMovie(): Flow<Resource<List<Movie>>> {
        return localDataSource.getFavoriteMovie().map {
            Resource.Success(DataMapper.mapEntitiesToDomain(it))
        }
    }

    override fun getMovieDetail(id: Int): Flow<Resource<MovieDetailResponse>> {
        return flow {
            emit(Resource.Loading())
            var isFavoriteMovie: Boolean
            runBlocking {
                isFavoriteMovie = localDataSource.isFavoriteMovie(id).first()
            }
            val apiResponse: ApiResponse<MovieDetailResponse> =
                remoteDataSource.getMovieDetail(id).first()
            when (apiResponse) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    isFavoriteMovie.let {
                        data.isFavoriteMovie = it
                    }
                    emit(Resource.Success(data))
                }

                is ApiResponse.Empty -> {
                }

                is ApiResponse.Error -> {
                    emit(
                        Resource.Error(apiResponse.errorMessage)
                    )
                }
            }
        }
    }


    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean): Boolean {
        var result = false

        // Create a new coroutine scope and launch a coroutine
        runBlocking {
            // Wait for the coroutine to complete and get the result
            result = withContext(Dispatchers.IO) {
                localDataSource.setFavoriteMovie(movie, state)
            }
        }

        // Return the result
        return result
    }
}