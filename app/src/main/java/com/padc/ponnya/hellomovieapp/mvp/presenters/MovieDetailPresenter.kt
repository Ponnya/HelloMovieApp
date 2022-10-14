package com.padc.ponnya.hellomovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padc.ponnya.hellomovieapp.mvp.views.MovieDetailView

interface MovieDetailPresenter: IBasePresenter {
    fun initView(view: MovieDetailView)
    fun onUiReadyInMovieDetail(owner: LifecycleOwner, movieId: Int)
    fun onTapBack()
}