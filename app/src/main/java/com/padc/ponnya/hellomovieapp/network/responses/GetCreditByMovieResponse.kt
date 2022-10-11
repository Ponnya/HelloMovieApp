package com.padc.ponnya.hellomovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO

data class GetCreditByMovieResponse(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("cast")
    val cast: List<ActorVO>?,

    @SerializedName("crew")
    val crew: List<ActorVO>?,
)