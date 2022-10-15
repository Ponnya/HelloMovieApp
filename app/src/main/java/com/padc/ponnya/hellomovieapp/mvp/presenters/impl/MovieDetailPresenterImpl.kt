package com.padc.ponnya.hellomovieapp.mvp.presenters.impl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.ponnya.hellomovieapp.activities.MovieDetailActivity
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl
import com.padc.ponnya.hellomovieapp.interactors.MovieInteractor
import com.padc.ponnya.hellomovieapp.interactors.MovieInteractorImpl
import com.padc.ponnya.hellomovieapp.mvp.presenters.MovieDetailPresenter
import com.padc.ponnya.hellomovieapp.mvp.views.MovieDetailView

class MovieDetailPresenterImpl: ViewModel(), MovieDetailPresenter {

    //Model
    private var mMovieInteractor: MovieInteractor = MovieInteractorImpl

    //View
    private var mView: MovieDetailView? = null

    override fun initView(view: MovieDetailView) {
        mView = view
    }

    override fun onUiReadyInMovieDetail(owner: LifecycleOwner, movieId: Int) {
        //Movie Details
        mMovieInteractor.getMovieDetail(movieId.toString()){
            mView?.showError(it)
        }?.observe(owner){
            it?.let {
                mView?.showMovieDetail(it)
            }
        }

        //Credits
        mMovieInteractor.getCreditByMovie(movieId.toString(), onSuccess = {
            mView?.showCreditsByMovie(it.first,it.second)
        }, onFailure = {
            mView?.showError(it)
        })
    }

    override fun onTapBack() {
        mView?.navigateBack()
    }

    override fun onUiReady(owner: LifecycleOwner) {

    }
}