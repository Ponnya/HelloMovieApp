package com.padc.ponnya.hellomovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.ponnya.hellomovieapp.data.vos.DateVO
import com.padc.ponnya.hellomovieapp.data.vos.MovieVO

data class MovieListResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("dates")
    val dates: DateVO?,
    @SerializedName("results")
    val results: List<MovieVO>?
)