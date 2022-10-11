package com.padc.ponnya.hellomovieapp.data.models

import androidx.lifecycle.LiveData
import com.padc.ponnya.hellomovieapp.data.vos.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

object MovieModelImpl : MovieModel, BaseModel() {

    //Data Agent
//    private val mMovieDataAgent: MovieDataAgent = RetrofitDataAgentImpl

//    //Database
//    private var mMovieDatabase: MovieDatabase? = null
//
//    fun initDatabase(context: Context) {
//        mMovieDatabase = MovieDatabase.getDBInstance(context)
//    }

    override fun getNowPlayingMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {
        //Network
        mMovieApi.getNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = NOW_PLAYING }

                //Database
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())


            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMoviesByType(NOW_PLAYING)
    }

    override fun getPopularMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {
        //Network
        mMovieApi.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = POPULAR }

                //Database
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())

            }, {
                onFailure(it.localizedMessage ?: "")
            })
        return mMovieDatabase?.movieDao()?.getMoviesByType(POPULAR)
    }

    override fun getTopRatedMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {
        //Network
        mMovieApi.getTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = TOP_RATED }
                //Database
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDatabase?.movieDao()?.getMoviesByType(TOP_RATED)
    }

    override fun getGenreList(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        //Network
        mMovieApi.getGenreList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it.genres ?: listOf()) },
                { onFailure(it.localizedMessage ?: "") })
    }

    override fun getMoviesByGenreId(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        //Network
        mMovieApi.getMoviesByGenreId(genreId = genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it.results ?: listOf()) },
                { onFailure(it.localizedMessage ?: "") })
    }

    override fun popularActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        //Network
        mMovieApi.popularActors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(it.results) },
                { onFailure(it.localizedMessage ?: "") })
    }

    override fun getMovieDetail(
        movieId: String,
        onFailure: (String) -> Unit
    ): LiveData<MovieVO?>? {

        //Network
        mMovieApi.getMovieDetail(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val movieFromDatabase =
                    mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId.toInt())
                it.type = movieFromDatabase?.type

                //Database
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)

            }, { onFailure(it.localizedMessage ?: "") })

        return mMovieDatabase?.movieDao()?.getMovieById(movieId.toInt())
    }

    override fun getCreditByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        //Network
        mMovieApi.getCreditByMovie(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf())) },
                { onFailure(it.localizedMessage ?: "") })

    }

    override fun searchMovie(query: String): Observable<List<MovieVO>> = mMovieApi
        .searchMovie(query = query)
        .map { it.results ?: listOf() }
        .onErrorResumeNext { Observable.just(listOf()) }
        .subscribeOn(Schedulers.io())
}