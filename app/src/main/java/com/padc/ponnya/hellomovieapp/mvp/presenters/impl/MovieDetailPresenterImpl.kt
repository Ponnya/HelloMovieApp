package com.padc.ponnya.hellomovieapp.mvp.presenters.impl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.ponnya.hellomovieapp.activities.MovieDetailActivity
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl
import com.padc.ponnya.hellomovieapp.mvp.presenters.MovieDetailPresenter
import com.padc.ponnya.hellomovieapp.mvp.views.MovieDetailView

class MovieDetailPresenterImpl: ViewModel(), MovieDetailPresenter {

    //Model
    private val mMovieModel = MovieModelImpl

    //View
    private var mView: MovieDetailView? = null

    override fun initView(view: MovieDetailView) {
        mView = view
    }

    override fun onUiReadyInMovieDetail(owner: LifecycleOwner, movieId: Int) {
        mMovieModel.getMovieDetail(
            movieId = movieId.toString(),
            onFailure = {
                mView?.showError(it)
            }
        )?.observe(owner){
            if (it != null) {
               mView?.showMovieDetail(it)
            }
        }
        mMovieModel.getCreditByMovie(
            movieId = movieId.toString(),
            onSuccess = {
               mView?.showCreditsByMovie(cast = it.first, crew = it.second)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapBack() {
      mView?.navigateBack()
    }

    override fun onUiReady(owner: LifecycleOwner) {

    }
}