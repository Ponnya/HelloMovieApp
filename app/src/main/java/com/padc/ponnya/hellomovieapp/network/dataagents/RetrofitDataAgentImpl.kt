/*
package com.padc.ponnya.hellomovieapp.network.dataagents

import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.data.vos.GenreVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import com.padc.ponnya.hellomovieapp.network.TheMovieApi
import com.padc.ponnya.hellomovieapp.network.responses.GetCreditByMovieResponse
import com.padc.ponnya.hellomovieapp.network.responses.GetGenresResponse
import com.padc.ponnya.hellomovieapp.network.responses.MovieListResponse
import com.padc.ponnya.hellomovieapp.network.responses.PopularActorResponse
import com.padc.ponnya.hellomovieapp.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitDataAgentImpl : MovieDataAgent {

    private var mTheMovieApi: TheMovieApi? = null

    init {
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mTheMovieApi = retrofit.create(TheMovieApi::class.java)

    }

    //Get Now Playing Movie
    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getNowPlayingMovies()?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: listOf()
                    onSuccess(movieList)
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    //Get Popular
    override fun getPopularMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getPopularMovies()?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: listOf()
                    onSuccess(movieList)
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    //Get Top Rated
    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getTopRatedMovies()?.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if (response.isSuccessful) {
                    val movieList = response.body()?.results ?: listOf()
                    onSuccess(movieList)
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getGenreList(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getGenreList()?.enqueue(object : Callback<GetGenresResponse> {
            override fun onResponse(
                call: Call<GetGenresResponse>,
                response: Response<GetGenresResponse>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()?.genres ?: listOf())
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<GetGenresResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getMoviesByGenreId(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getMoviesByGenreId(genreId = genreId)
            ?.enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful) {
                        onSuccess(response.body()?.results ?: listOf())
                    } else {
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun popularActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.popularActors()?.enqueue(object : Callback<PopularActorResponse> {
            override fun onResponse(
                call: Call<PopularActorResponse>,
                response: Response<PopularActorResponse>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()?.results ?: listOf())
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<PopularActorResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getMovieDetail(movieId = movieId)?.enqueue(object : Callback<MovieVO> {
            override fun onResponse(call: Call<MovieVO>, response: Response<MovieVO>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it)
                    }
                } else {
                    onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<MovieVO>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getCreditByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi?.getCreditByMovie(movieId = movieId)
            ?.enqueue(object : Callback<GetCreditByMovieResponse> {
                override fun onResponse(
                    call: Call<GetCreditByMovieResponse>,
                    response: Response<GetCreditByMovieResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf()))
                        }
                    } else {
                        onFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<GetCreditByMovieResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }
            })
    }
}*/
