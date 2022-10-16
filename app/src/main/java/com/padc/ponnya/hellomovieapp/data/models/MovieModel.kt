package com.padc.ponnya.hellomovieapp.data.models

import androidx.lifecycle.LiveData
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.data.vos.GenreVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Query

interface MovieModel {

    fun getNowPlayingMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>?

    fun getPopularMovies(
        onFailure: (String) -> Unit,
    ): LiveData<List<MovieVO>>?

    fun getTopRatedMovies(
        onFailure: (String) -> Unit,
    ): LiveData<List<MovieVO>>?

    fun getGenreList(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit,
    )

    fun getMoviesByGenreId(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit,
    )

    fun popularActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit,
    )

    fun getMovieDetail(
        movieId: String,
        onFailure: (String) -> Unit,
    ): LiveData<MovieVO?>?

    fun getCreditByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit,
    )

    fun searchMovie(
        query: String
    ): Observable<List<MovieVO>>


    //Reactive Streams Only
    fun getNowPlayingMoviesObservable(): Observable<List<MovieVO>>?
    fun getPopularMoviesObservable(): Observable<List<MovieVO>>?
    fun getTopRatedMoviesObservable(): Observable<List<MovieVO>>?
    fun getGenreListObservable(): Observable<List<GenreVO>>?
    fun popularActorsObservable(): Observable<List<ActorVO>>?
    fun getMoviesByGenreIdObservable(genreId: String): Observable<List<MovieVO>>?
    fun getMovieDetailObservable(movieId: Int): Observable<MovieVO?>
    fun getCreditByMovieObservable(movieId: Int): Observable<Pair<List<ActorVO>, List<ActorVO>>>

}