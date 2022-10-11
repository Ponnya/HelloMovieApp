package com.padc.ponnya.hellomovieapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.ponnya.hellomovieapp.R
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.viewholders.BestActorViewHolder

class BestActorsAdapter : RecyclerView.Adapter<BestActorViewHolder>() {

    private var mActorList: List<ActorVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestActorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_best_actor, parent, false)
        return BestActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestActorViewHolder, position: Int) {
        if (mActorList.isNotEmpty()) {
            holder.bindData(mActorList[position])
        }
    }

    override fun getItemCount(): Int = mActorList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(actorList: List<ActorVO>) {
        mActorList = actorList
        notifyDataSetChanged()
    }

}
