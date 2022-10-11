package com.padc.ponnya.hellomovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padc.ponnya.hellomovieapp.data.vos.CollectionVO

class CollectionTypeConverter {

    @TypeConverter
    fun toString(collectionVO: CollectionVO?): String {
        return Gson().toJson(collectionVO)
    }

    @TypeConverter
    fun toCollectionVO(collectionListJsonString: String): CollectionVO? {
        val collectionVOType = object : TypeToken<CollectionVO?>() {}.type
        return Gson().fromJson(collectionListJsonString, collectionVOType)
    }
}