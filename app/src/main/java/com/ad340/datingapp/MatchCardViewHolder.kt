package com.ad340.datingapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchCardViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    var matchImage: ImageView = itemView.findViewById(R.id.match_card_image)
    var matchName: TextView = itemView.findViewById(R.id.match_card_name)
}
