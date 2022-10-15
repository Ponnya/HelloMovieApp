package com.padc.ponnya.hellomovieapp.interactors

import androidx.lifecycle.LiveData
import com.padc.ponnya.hellomovieapp.data.models.MovieModel
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.data.vos.GenreVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO

import io.reactivex.rxjava3.core.Observable

object MovieInteractorImpl: MovieInteractor {
    //Model
    private val mMovieModel : MovieModel = MovieModelImpl
    override fun getNowPlayingMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        return mMovieModel.getNowPlayingMovies(onFailure)
    }

    override fun getPopularMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        return mMovieModel.getPopularMovies(onFailure)
    }

    override fun getTopRatedMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        return mMovieModel.getTopRatedMovies(onFailure)
    }

    override fun getGenreList(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        return mMovieModel.getGenreList(onSuccess,onFailure)
    }

    override fun getMoviesByGenreId(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        return mMovieModel.getMoviesByGenreId(genreId,onSuccess,onFailure)
    }

    override fun popularActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
        return mMovieModel.popularActors(onSuccess,onFailure)
    }

    override fun getMovieDetail(movieId: String, onFailure: (String) -> Unit): LiveData<MovieVO?>? {
        return mMovieModel.getMovieDetail(movieId,onFailure)
    }

    override fun getCreditByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        return mMovieModel.getCreditByMovie(movieId,onSuccess,onFailure)
    }

    override fun searchMovie(query: String): Observable<List<MovieVO>> {
        return mMovieModel.searchMovie(query)
    }

}