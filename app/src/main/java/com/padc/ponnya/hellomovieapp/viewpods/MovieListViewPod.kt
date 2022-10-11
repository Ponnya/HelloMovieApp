package com.padc.ponnya.hellomovieapp.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.ponnya.hellomovieapp.adapters.MovieListAdapter
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import com.padc.ponnya.hellomovieapp.delegates.MovieViewHolderDelegate
import kotlinx.android.synthetic.main.view_pod_movie_list.view.*

class MovieListViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    lateinit var mMovieListAdapter: MovieListAdapter
    lateinit var mMovieViewHolderDelegate: MovieViewHolderDelegate

    override fun onFinishInflate() {
        //setUpMovieRecyclerView()
        super.onFinishInflate()
    }

    fun setUpMovieListViewPod(delegate: MovieViewHolderDelegate) {
        setDelegate(delegate)
        setUpMovieRecyclerView()
    }

    fun setData(movieList: List<MovieVO>) {
        mMovieListAdapter.setNewData(movieList)
    }

    private fun setDelegate(delegate: MovieViewHolderDelegate) {
        this.mMovieViewHolderDelegate = delegate
    }

    private fun setUpMovieRecyclerView() {
        mMovieListAdapter = MovieListAdapter(mMovieViewHolderDelegate)
        rvMovieList.adapter = mMovieListAdapter
        rvMovieList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}