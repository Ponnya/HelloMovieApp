package com.padc.ponnya.hellomovieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.ponnya.hellomovieapp.R
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import com.padc.ponnya.hellomovieapp.delegates.MovieViewHolderDelegate
import com.padc.ponnya.hellomovieapp.viewholders.MovieListViewHolder

class MovieListAdapter(val mMovieViewHolderDelegate: MovieViewHolderDelegate) :
    RecyclerView.Adapter<MovieListViewHolder>() {

    private var mMovieList: List<MovieVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_movie_list, parent, false)
        return MovieListViewHolder(view, mMovieViewHolderDelegate)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        if (mMovieList.isNotEmpty()) {
            holder.bindData(mMovieList[position])
        }
    }

    override fun getItemCount(): Int = mMovieList.count()

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(movieList: List<MovieVO>) {
        mMovieList = movieList
        notifyDataSetChanged()
    }
}