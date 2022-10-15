package com.padc.ponnya.hellomovieapp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.padc.ponnya.hellomovieapp.R
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import com.padc.ponnya.hellomovieapp.mvvm.MovieDetailViewModel
import com.padc.ponnya.hellomovieapp.utils.IMAGE_BASE_URL
import com.padc.ponnya.hellomovieapp.viewpods.BestActorViewPod
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun newIntent(context: Context, movieId: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).putExtra(
                EXTRA_MOVIE_ID,
                movieId
            )
        }

    }

    lateinit var actorsViewPod: BestActorViewPod
    lateinit var creatorsViewPod: BestActorViewPod


    //Presenter
    private lateinit var mViewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        movieId?.let {
            setUpViewModel(it)
        }

        setUpViewPods()
        setUpListeners()

        observeLiveData()
    }

    private fun setUpViewModel(movieId: Int) {
        mViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        mViewModel.getInitialData(movieId)
    }

    private fun observeLiveData() {
        mViewModel.movieDetailsLiveData?.observe(this) {
            it?.let { movie ->
                bindData(movie)
            }
        }
        mViewModel.castLiveData.observe(this, actorsViewPod::setData)
        mViewModel.castLiveData.observe(this, creatorsViewPod::setData)
    }

    private fun setUpViewPods() {
        actorsViewPod = vpActors as BestActorViewPod
        actorsViewPod.setUpActorViewPod(
            backgroundColor = R.color.colorPrimary,
            titleText = getString(R.string.lbl_actors),
            moreTitleText = ""
        )
        creatorsViewPod = vpCreators as BestActorViewPod
        creatorsViewPod.setUpActorViewPod(
            backgroundColor = R.color.colorPrimary,
            titleText = getString(R.string.lbl_creators),
            moreTitleText = getString(R.string.lbl_more_creators)
        )
    }

    private fun setUpListeners() {
        btnBack.setOnClickListener {
            super.onBackPressed()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun bindData(movieVO: MovieVO) {
        Glide.with(this)
            .load("${IMAGE_BASE_URL}${movieVO.posterPath}")
            .into(ivMovieDetailProfile)
        tvMovieReleaseYear.text = movieVO.releaseDate?.substring(0, 4)
        tvMovieDetailName.text = movieVO.title ?: ""
        rbMovieDetailRating.rating = movieVO.getRatingBasedOnFiveStars()
        tvMovieDetailVoteCount.text = "${movieVO.voteCount ?: ""} VOTES"
        tvRating.text = movieVO.voteAverage?.toString() ?: ""
        toolbarLayout.title = movieVO.title ?: ""

        tvDuration.text = movieVO.getRuntimeWithHour()
        bindGenre(movieVO)

        tvOverview.text = movieVO.overview
        tvOriginalTitle.text = movieVO.originalTitle
        tvType.text = movieVO.getGenresAsCommaSeparatedString()
        tvProduction.text = movieVO.getGenresAsCommaSeparatedString()
        tvPremiere.text = movieVO.releaseDate ?: ""
        tvDescription.text = movieVO.overview ?: ""
    }

    private fun bindGenre(movieVO: MovieVO) {
        movieVO.genres?.let() {
            tvFirstGenre.text = it.firstOrNull()?.name ?: ""
            tvSecondGenre.text = it.getOrNull(1)?.name ?: ""
            tvThirdGenre.text = it.getOrNull(2)?.name ?: ""
            it.count().let { count ->
                if (count < 3) {
                    tvThirdGenre.visibility = View.GONE
                } else if (count < 2) {
                    tvSecondGenre.visibility = View.GONE
                }
            }
        }

    }


}