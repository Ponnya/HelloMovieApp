package com.padc.ponnya.hellomovieapp.mvi.mvibase

interface MVIView<S: MVIState> {
    fun render(state: S)
}