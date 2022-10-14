package com.padc.ponnya.hellomovieapp.mvp.presenters

import com.padc.ponnya.hellomovieapp.delegates.BannerViewHolderDelegate
import com.padc.ponnya.hellomovieapp.delegates.MovieViewHolderDelegate
import com.padc.ponnya.hellomovieapp.delegates.ShowcasesViewHolderDelegate
import com.padc.ponnya.hellomovieapp.mvp.views.MainView

interface MainPresenter: IBasePresenter, BannerViewHolderDelegate, MovieViewHolderDelegate,
    ShowcasesViewHolderDelegate {
        fun initView(view: MainView)
        fun onTapGenre(genrePosition: Int)
}