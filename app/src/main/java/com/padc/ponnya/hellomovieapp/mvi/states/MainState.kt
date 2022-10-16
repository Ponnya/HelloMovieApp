package com.padc.ponnya.hellomovieapp.mvi.states


import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.data.vos.GenreVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import com.padc.ponnya.hellomovieapp.mvi.mvibase.MVIState

data class MainState(
    val isloading: Boolean = false,
    val errorMessage: String = "",
    val nowPlayingMovies: List<MovieVO>,
    val popularMovies: List<MovieVO>,
    val topRatedMovies: List<MovieVO>,
    val genres: List<GenreVO>,
    var moviesByGenre: List<MovieVO>,
    val actors: List<ActorVO>
): MVIState {
    companion object{
        fun idle(): MainState = MainState(
            isloading = false,
            errorMessage = "",
            nowPlayingMovies = listOf(),
            popularMovies = listOf(),
            topRatedMovies = listOf(),
            genres = listOf(),
            moviesByGenre = listOf(),
            actors = listOf()
        )
    }
}
