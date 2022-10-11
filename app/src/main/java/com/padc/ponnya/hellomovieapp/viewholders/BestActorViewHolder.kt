package com.padc.ponnya.hellomovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.ponnya.hellomovieapp.data.vos.ActorVO
import com.padc.ponnya.hellomovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_best_actor.view.*

class BestActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(actor: ActorVO) {
        Glide.with(itemView.context)
            .load("${IMAGE_BASE_URL}${actor.profilePath}")
            .into(itemView.ivBestActors)
        itemView.tvActorName.text = actor.name
    }
}