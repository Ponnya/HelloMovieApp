package com.padc.ponnya.hellomovieapp.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO
import io.reactivex.rxjava3.core.Flowable

@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    fun insertMovies(movies: List<MovieVO>)

    @Insert(onConflict = REPLACE)
    fun insertSingleMovie(movieVO: MovieVO?)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieVO>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieVO?>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieByIdOneTime(movieId: Int): MovieVO?

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getMoviesByType(type: String): LiveData<List<MovieVO>>

    @Query("DELETE FROM movies")
    fun deleteAllMovies()

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getMoviesByTypeFlowable(type: String): Flowable<List<MovieVO>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMoviesByIdFlowable(movieId: Int): Flowable<MovieVO>
}