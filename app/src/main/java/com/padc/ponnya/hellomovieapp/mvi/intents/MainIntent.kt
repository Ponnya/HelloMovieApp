package com.padc.ponnya.hellomovieapp.mvi.intents

import com.padc.ponnya.hellomovieapp.mvi.mvibase.MVIIntent


sealed class MainIntent : MVIIntent {
    class LoadMoviesByGenreIntent(val genrePosition: Int) : MainIntent()
    object LoadAllHomePageData : MainIntent()
}
