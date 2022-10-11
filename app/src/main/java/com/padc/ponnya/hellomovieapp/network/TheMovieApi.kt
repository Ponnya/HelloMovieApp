package com.padc.ponnya.hellomovieapp.network

import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import com.padc.ponnya.hellomovieapp.network.responses.GetCreditByMovieResponse
import com.padc.ponnya.hellomovieapp.network.responses.GetGenresResponse
import com.padc.ponnya.hellomovieapp.network.responses.MovieListResponse
import com.padc.ponnya.hellomovieapp.network.responses.PopularActorResponse
import com.padc.ponnya.hellomovieapp.utils.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieApi {

    @GET(API_GET_NOW_PLAYING)
    fun getNowPlayingMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ): Observable<MovieListResponse>

    @GET(API_GET_POPULAR)
    fun getPopularMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ): Observable<MovieListResponse>

    @GET(API_GET_TOP_RATED)
    fun getTopRatedMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ): Observable<MovieListResponse>

    @GET(API_GET_GENRE_LIST)
    fun getGenreList(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
    ): Observable<GetGenresResponse>

    @GET(API_GET_MOVIES_BY_GENRE_ID)
    fun getMoviesByGenreId(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_WITH_GENRES) genreId: String,
    ): Observable<MovieListResponse>

    @GET(API_POPULAR_ACTORS)
    fun popularActors(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ): Observable<PopularActorResponse>

    @GET("$API_GET_MOVIE_DETAIL/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
    ): Observable<MovieVO>

    @GET("$API_GET_CREDIT_BY_MOVIE/{movie_id}/credits")
    fun getCreditByMovie(
        @Path("movie_id") movieId: String,
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
    ): Observable<GetCreditByMovieResponse>

    @GET(API_SEARCH_MOVIE)
    fun searchMovie(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_QUERY) query: String
    ): Observable<MovieListResponse>
}