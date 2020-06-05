package com.ad340.datingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.match_card.view.*

/**
 * Adapter used to show a list of card matches.
 */
class MatchCardAdapter internal constructor(
    context: Context,
    val matchViewModel: FirebaseMatchViewModel
): RecyclerView.Adapter<MatchCardAdapter.MatchCardViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var matchList = emptyList<MatchItem>()


    inner class MatchCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var matchImage: ImageView = itemView.findViewById(R.id.match_card_image)
        var matchName: TextView = itemView.findViewById(R.id.match_card_name)
        var matchIsLiked: CheckBox = itemView.findViewById(R.id.match_like_button)
        var it = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchCardViewHolder {
        val layoutView = inflater.inflate(R.layout.match_card, parent, false)


        layoutView.match_like_button.setOnClickListener {
            val matchName = layoutView.match_card_name.text
            val liked = (it as CheckBox).isChecked
            val uid = layoutView.tag as String

            matchViewModel.updateMatchLikeStatus(uid, liked)

            val toastStr: String = if (liked) {
                String.format(layoutView.resources.getString(R.string.you_liked), matchName)
            } else {
                String.format(layoutView.resources.getString(R.string.you_unliked), matchName)
            }
            Toast.makeText(parent.context, toastStr, Toast.LENGTH_SHORT).show()
        }

        return MatchCardViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: MatchCardViewHolder, position: Int) {
        if (position < matchList.size) {
            val match = matchList[position]

            Picasso.get().load(match.imageUrl).into(holder.matchImage)
            holder.matchName.text = match.name
            holder.matchIsLiked.isChecked = match.liked
            holder.it.tag = match.uid
        }
    }

    override fun getItemCount() = matchList.size

    internal fun setMatchList(matchList: List<MatchItem>) {
        this.matchList = matchList
        notifyDataSetChanged()
    }
}