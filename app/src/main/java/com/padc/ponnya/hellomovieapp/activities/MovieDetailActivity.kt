package com.padc.ponnya.hellomovieapp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.padc.ponnya.hellomovieapp.R
import com.padc.ponnya.hellomovieapp.data.models.MovieModel
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import com.padc.ponnya.hellomovieapp.mvp.presenters.IBasePresenter
import com.padc.ponnya.hellomovieapp.mvp.presenters.MovieDetailPresenter
import com.padc.ponnya.hellomovieapp.mvp.presenters.impl.MovieDetailPresenterImpl
import com.padc.ponnya.hellomovieapp.mvp.views.MovieDetailView
import com.padc.ponnya.hellomovieapp.utils.IMAGE_BASE_URL
import com.padc.ponnya.hellomovieapp.viewpods.BestActorViewPod
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : BaseActivity(), MovieDetailView {

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun newIntent(context: Context, movieId: Int): Intent {
            return Intent(context, MovieDetailActivity::class.java).putExtra(
                EXTRA_MOVIE_ID,
                movieId
            )
        }

    }

    //View Pod
    lateinit var actorsViewPod: BestActorViewPod
    lateinit var creatorsViewPod: BestActorViewPod

    //Presenter
    private lateinit var mPresenter: MovieDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setUpPresenter()

        setUpViewPods()
        setUpListeners()

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        movieId?.let {
          mPresenter.onUiReadyInMovieDetail(this,it)
        }
    }

    private fun setUpPresenter(){
        mPresenter = ViewModelProvider(this)[MovieDetailPresenterImpl::class.java]
        mPresenter.initView(this)
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
            mPresenter.onTapBack()
        }

    }


    override fun showMovieDetail(movie: MovieVO) {
       bindData(movie)
    }

    override fun showCreditsByMovie(cast: List<ActorVO>, crew: List<ActorVO>) {
      actorsViewPod.setData(cast)
        creatorsViewPod.setData(crew)
    }

    override fun navigateBack() {
      finish()
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