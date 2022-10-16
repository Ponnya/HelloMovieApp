package com.padc.ponnya.hellomovieapp.mvi.processors

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.padc.ponnya.hellomovieapp.data.models.MovieModel
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl
import com.padc.ponnya.hellomovieapp.mvi.states.MainState
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

object MainProcessor {

    private val mMovieModel: MovieModel = MovieModelImpl

    fun loadAllHomePageData(
        previousState: MainState
    ):LiveData<MainState>{
        return Observable.zip(
            mMovieModel.getNowPlayingMoviesObservable(),
            mMovieModel.getPopularMoviesObservable(),
            mMovieModel.getTopRatedMoviesObservable(),
            mMovieModel.getGenreListObservable(),
            mMovieModel.popularActorsObservable()
        ){
            nowPlayingMovies,popularMovies,topRatedMovies,genres,actors ->
            return@zip previousState.copy(
                isloading = false,
                errorMessage = "",
                nowPlayingMovies = nowPlayingMovies,
                popularMovies = popularMovies,
                topRatedMovies = topRatedMovies,
                genres = genres,
                moviesByGenre = previousState.moviesByGenre,
                actors = actors,
            )
        }.toFlowable(BackpressureStrategy.BUFFER).toLiveData()
    }

    fun loadMoviesByGenre(
        previousState: MainState,
        genreId: Int
    ): LiveData<MainState>{
        return mMovieModel.getMoviesByGenreIdObservable(genreId.toString())
            ?.map {
                previousState.copy(
                    isloading = false,
                    errorMessage = "",
                    nowPlayingMovies = previousState.nowPlayingMovies,
                    popularMovies = previousState.popularMovies,
                    topRatedMovies = previousState.topRatedMovies,
                    genres = previousState.genres,
                    moviesByGenre = it,
                    actors = previousState.actors
                )
            }?.subscribeOn(Schedulers.io())
            ?.toFlowable(BackpressureStrategy.BUFFER)
            ?.toLiveData()!!
    }

}