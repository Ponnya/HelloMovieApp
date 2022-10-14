package com.padc.ponnya.hellomovieapp.mvp.views

import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.data.vos.GenreVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO

interface MainView : BaseView {
    fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>)
    fun showPopularMovies(poplarMovies: List<MovieVO>)
    fun showTopRatedMovies(topRateMovies: List<MovieVO>)
    fun showGenres(genreList: List<GenreVO>)
    fun showMoviesByGenre(moviesByGenre: List<MovieVO>)
    fun showActors(actors: List<ActorVO>)
    fun navigateToMovieDetailsScreen(movieId: Int)
}