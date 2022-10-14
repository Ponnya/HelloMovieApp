package com.padc.ponnya.hellomovieapp.mvp.views

import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO

interface MovieDetailView : BaseView {
    fun showMovieDetail(movie: MovieVO)
    fun showCreditsByMovie(cast: List<ActorVO>, crew: List<ActorVO>)
    fun navigateBack()
}