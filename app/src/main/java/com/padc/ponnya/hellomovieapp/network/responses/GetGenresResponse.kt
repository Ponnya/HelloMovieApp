package com.padc.ponnya.hellomovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.ponnya.hellomovieapp.data.vos.GenreVO

data class GetGenresResponse(

    @SerializedName("genres")
    val genres: List<GenreVO>?
)