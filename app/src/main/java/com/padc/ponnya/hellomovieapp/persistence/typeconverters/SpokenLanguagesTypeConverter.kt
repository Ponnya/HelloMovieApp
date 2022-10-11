package com.padc.ponnya.hellomovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.ponnya.hellomovieapp.data.vos.SpokenLanguageVO

class SpokenLanguagesTypeConverter {

    @TypeConverter
    fun toString(spokenLanguageList: List<SpokenLanguageVO>?): String {
        return Gson().toJson(spokenLanguageList)
    }

    @TypeConverter
    fun toSpokenLanguageList(spokenLanguageListJsonString: String): List<SpokenLanguageVO>? {
        val spokenLanguageListType = object : TypeToken<List<SpokenLanguageVO>?>() {}.type
        return Gson().fromJson(spokenLanguageListJsonString, spokenLanguageListType)
    }
}