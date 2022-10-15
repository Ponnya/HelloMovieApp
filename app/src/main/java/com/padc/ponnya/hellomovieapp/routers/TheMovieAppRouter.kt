package com.padc.ponnya.hellomovieapp.routers

import android.app.Activity
import com.padc.ponnya.hellomovieapp.activities.MovieDetailActivity
import com.padc.ponnya.hellomovieapp.activities.SearchMoviesActivity

fun Activity.navigateToMovieDetailsActivity(movieId: Int){
    startActivity(MovieDetailActivity.newIntent(this,movieId))
}

fun Activity.navigateToMovieSearchActivity(){
    startActivity(SearchMoviesActivity.newIntent(this))
}