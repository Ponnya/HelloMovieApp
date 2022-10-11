package com.padc.ponnya.hellomovieapp

import android.app.Application
import com.padc.ponnya.hellomovieapp.data.models.BaseModel
import com.padc.ponnya.hellomovieapp.data.models.MovieModelImpl

class MovieApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        MovieModelImpl.initDatabase(applicationContext)
    }
}