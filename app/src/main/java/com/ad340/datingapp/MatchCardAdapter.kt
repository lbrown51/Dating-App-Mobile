package com.ad340.datingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter used to show a list of card matches.
 */
class MatchCardAdapter(private val matchList: List<MatchEntry>):
    RecyclerView.Adapter<MatchCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchCardViewHolder {
        val layoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_card, parent, false)

        return MatchCardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: MatchCardViewHolder, position: Int) {
        if (position < matchList.size) {
            val match = matchList[position]
            holder.matchImage.setImageResource(R.drawable.fae_profile)
            holder.matchName.text = match.name
        }
    }

    override fun getItemCount(): Int {
        return matchList.size
    }


}