package com.padc.ponnya.hellomovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO


class MovieDetailViewModel : ViewModel() {
    //Model
    private val mMovieModel = MovieModelImpl

    //LiveData
    var movieDetailsLiveData: LiveData<MovieVO?>? = null
    val castLiveData = MutableLiveData<List<ActorVO>>()
    val crewLiveData = MutableLiveData<List<ActorVO>>()
    val mErrorLiveData = MutableLiveData<String>()

    fun getInitialData(movieId: Int) {
        movieDetailsLiveData = mMovieModel.getMovieDetail(movieId.toString()) {
            mErrorLiveData.postValue(it)
        }
        mMovieModel.getCreditByMovie(movieId.toString(), onSuccess = {
            castLiveData.postValue(it.first ?: listOf())
            crewLiveData.postValue(it.second ?: listOf())
        },
            onFailure = {
                mErrorLiveData.postValue(it)
            })
    }
}