package com.padc.ponnya.hellomovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import com.padc.ponnya.hellomovieapp.R
import com.padc.ponnya.hellomovieapp.adapters.MovieListAdapter
import com.padc.ponnya.hellomovieapp.data.models.MovieModel
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl
import com.padc.ponnya.hellomovieapp.delegates.MovieViewHolderDelegate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search_movies.*
import java.util.concurrent.TimeUnit

class SearchMoviesActivity : AppCompatActivity(), MovieViewHolderDelegate {


    private lateinit var mMovieListAdapter: MovieListAdapter

    //Model
    private val mMovieModel: MovieModel = MovieModelImpl

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchMoviesActivity::class.java)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movies)

        setUpRecyclerView()
        setUpListeners()
    }

    private fun setUpListeners() {
        edtSearch.textChanges()
            .debounce(500L, TimeUnit.MILLISECONDS)
            .flatMap { mMovieModel.searchMovie(it.toString()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mMovieListAdapter.setNewData(it)
            }, { showError(it.localizedMessage ?: "") })
    }

    private fun showError(errorMsg: String) {
        Snackbar.make(window.decorView, errorMsg, Snackbar.LENGTH_SHORT).show()
    }

    private fun setUpRecyclerView() {
        mMovieListAdapter = MovieListAdapter(this)
        rvMovies.adapter = mMovieListAdapter
        rvMovies.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onTapMovieItem(movieId: Int) {
        TODO("Not yet implemented")
    }
}