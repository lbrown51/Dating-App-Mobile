package com.ad340.datingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.match_card.view.*

/**
 * Adapter used to show a list of card matches.
 */
class MatchCardAdapter(private val matchList: List<MatchEntry>):
    RecyclerView.Adapter<MatchCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchCardViewHolder {
        val layoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_card, parent, false)

        layoutView.match_like_button.setOnClickListener {
            val matchName = layoutView.match_card_name.text
            val youLikedStr = String.format(
                layoutView.resources.getString(R.string.you_liked), matchName)
            Toast.makeText(parent.context, youLikedStr, Toast.LENGTH_LONG).show()
        }

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