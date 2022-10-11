package com.padc.ponnya.hellomovieapp.data.vos

import com.google.gson.annotations.SerializedName

data class ProductionCountrieVO(
    @SerializedName("iso_3166_1")
    val iSO: String?,

    @SerializedName("name")
    val name: String?,
)
