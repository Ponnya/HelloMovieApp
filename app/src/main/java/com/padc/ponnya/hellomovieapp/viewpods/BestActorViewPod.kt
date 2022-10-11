package com.padc.ponnya.hellomovieapp.viewpods

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.padc.ponnya.hellomovieapp.adapters.BestActorsAdapter
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import kotlinx.android.synthetic.main.view_pod_best_actor.view.*

class BestActorViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    lateinit var mBestActorsAdapter: BestActorsAdapter

    override fun onFinishInflate() {
        setUpBestActorViewHolder()
        super.onFinishInflate()
    }

    fun setUpActorViewPod(backgroundColor: Int, titleText: String, moreTitleText: String) {
        setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
        tvBestActors.text = titleText
        tvMoreActors.text = moreTitleText
        tvMoreActors.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    fun setData(actorList: List<ActorVO>){
        mBestActorsAdapter.setNewData(actorList)
    }

    private fun setUpBestActorViewHolder() {
        mBestActorsAdapter = BestActorsAdapter()
        rvBestActors.adapter = mBestActorsAdapter
        rvBestActors.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

}