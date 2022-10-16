package com.padc.ponnya.hellomovieapp.mvi.mvibase

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

interface MVIViewModel<S : MVIState, I : MVIIntent> {
    val state: MutableLiveData<S>
    fun processIntent(intent: I,lifeCycleOwner: LifecycleOwner)
}