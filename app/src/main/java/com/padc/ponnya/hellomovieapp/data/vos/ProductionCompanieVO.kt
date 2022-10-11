package com.padc.ponnya.hellomovieapp.data.vos

import com.google.gson.annotations.SerializedName

data class ProductionCompanieVO(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("logo_path")
    val logoPath: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("origin_country")
    val originCountry: String?,
)