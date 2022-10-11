package com.padc.ponnya.hellomovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO

data class PopularActorResponse(
    @SerializedName("results")
    val results: List<ActorVO>,
)