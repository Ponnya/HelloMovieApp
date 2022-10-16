package com.padc.ponnya.hellomovieapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.padc.ponnya.hellomovieapp.R
import com.padc.ponnya.hellomovieapp.adapters.BannerAdapter
import com.padc.ponnya.hellomovieapp.adapters.ShowcasesAdapter
import com.padc.ponnya.hellomovieapp.data.models.MovieModel
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl
import com.padc.ponnya.hellomovieapp.data.vos.GenreVO
import com.padc.ponnya.hellomovieapp.delegates.BannerViewHolderDelegate
import com.padc.ponnya.hellomovieapp.delegates.MovieViewHolderDelegate
import com.padc.ponnya.hellomovieapp.delegates.ShowcasesViewHolderDelegate
import com.padc.ponnya.hellomovieapp.mvi.intents.MainIntent
import com.padc.ponnya.hellomovieapp.mvi.mvibase.MVIView
import com.padc.ponnya.hellomovieapp.mvi.states.MainState
import com.padc.ponnya.hellomovieapp.mvi.viewmodels.MainViewModel
import com.padc.ponnya.hellomovieapp.viewpods.BestActorViewPod
import com.padc.ponnya.hellomovieapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BannerViewHolderDelegate, MovieViewHolderDelegate,
    ShowcasesViewHolderDelegate, MVIView<MainState> {

    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowcasesAdapter: ShowcasesAdapter

    lateinit var mBestPopularMovieListAndSeriesViewPod: MovieListViewPod
    lateinit var mMoviesByGenreViewPod: MovieListViewPod
    lateinit var mBestActorViewPod: BestActorViewPod


    //View Model
    private lateinit var mViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        setUpToolBar()
        setUpBannerViewPager()
        //setUpGenreTabLayout()
        setUpRecyclerViewShowcases()

        setUpListeners()
        setUpViewPods()
        //MovieDataAgentImpl.getNowPlayingMovies()
        //OkHttpDataAgentImpl.getNowPlayingMovies()

        setInitialIntents()
        observeState()


    }
    private fun setUpViewModel() {
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun setInitialIntents() {
        mViewModel.processIntent(MainIntent.LoadAllHomePageData, this)
    }

    private fun observeState() {
        mViewModel.state.observe(this, this::render)
    }



    private fun setUpViewPods() {
        mBestPopularMovieListAndSeriesViewPod = vpBestPopularFilmsAmdSeries as MovieListViewPod
        mBestPopularMovieListAndSeriesViewPod.setUpMovieListViewPod(this)

        mMoviesByGenreViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenreViewPod.setUpMovieListViewPod(this)

        mBestActorViewPod = vpBestActor as BestActorViewPod

    }

    private fun setUpRecyclerViewShowcases() {
        mShowcasesAdapter = ShowcasesAdapter(this)
        rvShowcases.adapter = mShowcasesAdapter
        rvShowcases.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpListeners() {
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mViewModel.processIntent(
                    MainIntent.LoadMoviesByGenreIntent(tab?.position ?: 0), this@MainActivity
                )
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
        mBannerAdapter = BannerAdapter(this)
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

    private fun showError(errorMsg: String) {
        Snackbar.make(window.decorView, errorMsg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //App Bar Trailing Icon
        menuInflater.inflate(R.menu.menu_discover, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      startActivity(SearchMoviesActivity.newIntent(this))


        return  super.onOptionsItemSelected(item)
    }




    override fun onTapMovieFromBanner(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }

    override fun onTapMovieItem(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }

    override fun onTapMovieFromShowcase(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }

    override fun render(state: MainState) {
        if (state.errorMessage.isNotEmpty()) {
            showError(state.errorMessage)
        }
        mBannerAdapter.setNewData(state.nowPlayingMovies)
        mBestPopularMovieListAndSeriesViewPod.setData(state.popularMovies)
        mShowcasesAdapter.setNewData(state.topRatedMovies)
        setUpGenreTabLayout(state.genres)
        mMoviesByGenreViewPod.setData(state.moviesByGenre)
        mBestActorViewPod.setData(state.actors)

    }
}