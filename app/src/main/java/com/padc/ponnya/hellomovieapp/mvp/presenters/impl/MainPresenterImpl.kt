package com.padc.ponnya.hellomovieapp.mvp.presenters.impl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.ponnya.hellomovieapp.data.models.MovieModel
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl
import com.padc.ponnya.hellomovieapp.data.vos.GenreVO
import com.padc.ponnya.hellomovieapp.mvp.presenters.MainPresenter
import com.padc.ponnya.hellomovieapp.mvp.views.MainView

class MainPresenterImpl: ViewModel(), MainPresenter {

    //View
    var mView: MainView? = null

    //Model
    private val mMovieModel: MovieModel = MovieModelImpl

    //States
    private var mGenres: List<GenreVO>? = listOf()

    override fun initView(view: MainView) {
       mView = view
    }

    override fun onUiReady(owner: LifecycleOwner) {
        //Now Playing Movies
        mMovieModel.getNowPlayingMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showNowPlayingMovies(it)
        }


        //Popular Movies
        mMovieModel.getPopularMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showPopularMovies(it)
        }

        //Top Rated Movies
        mMovieModel.getTopRatedMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showTopRatedMovies(it)
        }

        //Genre List
        mMovieModel.getGenreList(
            onSuccess = {
                mGenres = it
               mView?.showGenres(it)

                //Get Movies By Genre For First Genre
                it.firstOrNull()?.id?.let { genreId ->
                    onTapGenre(genreId)
                }
            },
            onFailure = {
                mView?.showError(it)
            }
        )

        mMovieModel.popularActors(
            onSuccess = {
                mView?.showActors(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapGenre(genrePosition: Int) {
        mGenres?.getOrNull(genrePosition)?.id?.let { genreId->
            mMovieModel.getMoviesByGenreId(genreId = genreId.toString(),
            onSuccess = {
                mView?.showMoviesByGenre(it)
            },
            onFailure = {
                mView?.showError(it)
            })
        }
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onTapMovieItem(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onTapMovieFromShowcase(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }
}