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

    override fun getNowPlayingMoviesObservable(): Observable<List<MovieVO>>? {
        mMovieApi.getNowPlayingMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.results?.forEach { movie -> movie.type = NOW_PLAYING }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }
        return mMovieDatabase?.movieDao()?.getMoviesByTypeFlowable(type = NOW_PLAYING)
            ?.toObservable()
    }

    override fun getPopularMoviesObservable(): Observable<List<MovieVO>>? {
        mMovieApi.getPopularMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.results?.forEach { movie -> movie.type = POPULAR }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }
        return mMovieDatabase?.movieDao()?.getMoviesByTypeFlowable(type = POPULAR)?.toObservable()
    }

    override fun getTopRatedMoviesObservable(): Observable<List<MovieVO>>? {
        mMovieApi.getTopRatedMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.results?.forEach { movie -> movie.type = TOP_RATED }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }
        return mMovieDatabase?.movieDao()?.getMoviesByTypeFlowable(type = TOP_RATED)?.toObservable()
    }

    override fun getGenreListObservable(): Observable<List<GenreVO>>? {
        return mMovieApi.getGenreList()
            .map { it.genres ?: listOf() }
            .subscribeOn(Schedulers.io())
    }

    override fun popularActorsObservable(): Observable<List<ActorVO>>? {
        return mMovieApi.popularActors()
            .map { it.results ?: listOf() }
            .subscribeOn(Schedulers.io())
    }

    override fun getMoviesByGenreIdObservable(genreId: String): Observable<List<MovieVO>>? {
        return mMovieApi.getMoviesByGenreId(genreId = genreId)
            .map { it.results ?: listOf() }
            .subscribeOn(Schedulers.io())
    }

    override fun getMovieDetailObservable(movieId: Int): Observable<MovieVO?> {
        mMovieApi.getMovieDetail(movieId = movieId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val movieFromDatabaseToSync =
                    mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId = movieId.toInt())
                it.type = movieFromDatabaseToSync?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)
            }
        return mMovieDatabase?.movieDao()?.getMoviesByIdFlowable(movieId = movieId)
            ?.toObservable()!!
    }

    override fun getCreditByMovieObservable(movieId: Int): Observable<Pair<List<ActorVO>, List<ActorVO>>> {
        return mMovieApi.getCreditByMovie(movieId = movieId.toString())
            .map{Pair(it.cast ?: listOf(),it.crew ?: listOf())}
            .subscribeOn(Schedulers.io())
    }
}