package com.padc.ponnya.hellomovieapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
import com.padc.ponnya.hellomovieapp.viewpods.BestActorViewPod
import com.padc.ponnya.hellomovieapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BannerViewHolderDelegate, MovieViewHolderDelegate,
    ShowcasesViewHolderDelegate {

    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowcasesAdapter: ShowcasesAdapter

    lateinit var mBestPopularMovieListAndSeriesViewPod: MovieListViewPod
    lateinit var mMoviesByGenreViewPod: MovieListViewPod
    lateinit var mBestActorViewPod: BestActorViewPod


    //Model
    private val mMovieModel: MovieModel = MovieModelImpl

    //Data
    private var mGenres: List<GenreVO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolBar()
        setUpBannerViewPager()
        //setUpGenreTabLayout()
        setUpRecyclerViewShowcases()

        setUpListeners()
        setUpViewPods()
        //MovieDataAgentImpl.getNowPlayingMovies()
        //OkHttpDataAgentImpl.getNowPlayingMovies()

        requestData()

    }

    private fun requestData() {

        //Now Playing Movies
        mMovieModel.getNowPlayingMovies {
            showError(it)
        }?.observe(this) {
            mBannerAdapter.setNewData(it)
        }


        //Popular Movies
        mMovieModel.getPopularMovies {
            showError(it)
        }?.observe(this) {
            mBestPopularMovieListAndSeriesViewPod.setData(it)
        }

        //Top Rated Movies
        mMovieModel.getTopRatedMovies {
            showError(it)
        }?.observe(this) {
            mShowcasesAdapter.setNewData(it)
        }

        //Genre List
        mMovieModel.getGenreList(
            onSuccess = {
                mGenres = it
                setUpGenreTabLayout(it)

                //Get Movies By Genre For First Genre
                it.firstOrNull()?.id?.let { genreId ->
                    getMoviesByGenreId(genreId)
                }
            },
            onFailure = {
                showError(it)
            }
        )

        mMovieModel.popularActors(
            onSuccess = {
                mBestActorViewPod.setData(it)
            },
            onFailure = {
                showError(it)
            }
        )
    }

    private fun getMoviesByGenreId(genreId: Int) {
        mMovieModel.getMoviesByGenreId(
            genreId = genreId.toString(),
            onSuccess = {
                mMoviesByGenreViewPod.setData(it)
            },
            onFailure = {
                showError(it)
            }
        )

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
                mGenres?.getOrNull(tab?.position ?: 0)?.id?.let {
                    getMoviesByGenreId(it)
                }
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
}