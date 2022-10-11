package com.padc.ponnya.hellomovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import com.padc.ponnya.hellomovieapp.delegates.MovieViewHolderDelegate
import com.padc.ponnya.hellomovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_item_movie_list.view.*

class MovieListViewHolder(
    itemView: View,
    private val mMovieViewHolderDelegate: MovieViewHolderDelegate
) : RecyclerView.ViewHolder(itemView) {

    private var mMovieVO: MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovieVO?.let { movie ->
                mMovieViewHolderDelegate.onTapMovieItem(movie.id)
            }

        }
    }

    fun bindData(movie: MovieVO) {
        mMovieVO = movie

        Glide.with(itemView.context)
            .load("${IMAGE_BASE_URL}${movie.posterPath}")
            .into(itemView.ivMovieImage)
        itemView.tvMovieName.text = movie.title
        itemView.tvMovieRating.text = movie.voteAverage?.toString()
        itemView.rbMovieRating.rating = movie.getRatingBasedOnFiveStars()
    }
}