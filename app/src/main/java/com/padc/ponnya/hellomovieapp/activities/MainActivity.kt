package com.padc.ponnya.hellomovieapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.padc.ponnya.hellomovieapp.R
import com.padc.ponnya.hellomovieapp.adapters.BannerAdapter
import com.padc.ponnya.hellomovieapp.adapters.ShowcasesAdapter
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.data.vos.GenreVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import com.padc.ponnya.hellomovieapp.mvp.presenters.MainPresenter
import com.padc.ponnya.hellomovieapp.mvp.presenters.impl.MainPresenterImpl
import com.padc.ponnya.hellomovieapp.mvp.views.MainView
import com.padc.ponnya.hellomovieapp.viewpods.BestActorViewPod
import com.padc.ponnya.hellomovieapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowcasesAdapter: ShowcasesAdapter

    lateinit var mBestPopularMovieListAndSeriesViewPod: MovieListViewPod
    lateinit var mMoviesByGenreViewPod: MovieListViewPod
    lateinit var mBestActorViewPod: BestActorViewPod

    //Presenter
    private lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpPresenter()

        setUpToolBar()
        setUpBannerViewPager()
        //setUpGenreTabLayout()
        setUpRecyclerViewShowcases()

        setUpListeners()
        setUpViewPods()
        //MovieDataAgentImpl.getNowPlayingMovies()
        //OkHttpDataAgentImpl.getNowPlayingMovies()

        mPresenter.onUiReady(this)

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MainPresenterImpl::class.java]
        mPresenter.initView(this)
    }


    private fun setUpViewPods() {
        mBestPopularMovieListAndSeriesViewPod = vpBestPopularFilmsAmdSeries as MovieListViewPod
        mBestPopularMovieListAndSeriesViewPod.setUpMovieListViewPod(mPresenter)

        mMoviesByGenreViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenreViewPod.setUpMovieListViewPod(mPresenter)

        mBestActorViewPod = vpBestActor as BestActorViewPod

    }

    private fun setUpRecyclerViewShowcases() {
        mShowcasesAdapter = ShowcasesAdapter(mPresenter)
        rvShowcases.adapter = mShowcasesAdapter
        rvShowcases.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpListeners() {
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mPresenter.onTapGenre(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


    }

    private fun setUpGenreTabLayout(genreList: List<GenreVO>) {
        genreList.forEach {
            tabLayoutGenre.newTab().apply {
                text = it.name
                tabLayoutGenre.addTab(this)
            }
        }
    }

    private fun setUpBannerViewPager() {
        mBannerAdapter = BannerAdapter(mPresenter)
        viewPagerBanner.adapter = mBannerAdapter

        dotsIndicator.attachTo(viewPagerBanner)
    }

    private fun setUpToolBar() {
        setSupportActionBar(toolbar)

        // App Bar Leading Icon
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //App Bar Trailing Icon
        menuInflater.inflate(R.menu.menu_discover, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(SearchMoviesActivity.newIntent(this))


        return super.onOptionsItemSelected(item)
    }

    override fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>) {
        mBannerAdapter.setNewData(nowPlayingMovies)
    }

    override fun showPopularMovies(poplarMovies: List<MovieVO>) {
        mBestPopularMovieListAndSeriesViewPod.setData(poplarMovies)
    }

    override fun showTopRatedMovies(topRateMovies: List<MovieVO>) {
        mShowcasesAdapter.setNewData(topRateMovies)
    }

    override fun showGenres(genreList: List<GenreVO>) {
        setUpGenreTabLayout(genreList)
    }

    override fun showMoviesByGenre(moviesByGenre: List<MovieVO>) {
        mMoviesByGenreViewPod.setData(moviesByGenre)
    }

    override fun showActors(actors: List<ActorVO>) {
        mBestActorViewPod.setData(actors)
    }

    override fun navigateToMovieDetailsScreen(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }


}