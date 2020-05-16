package com.ad340.datingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_matches.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [Matches.newInstance] factory method to
 * create an instance of this fragment.
 */
class Matches : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get view
        val view = inflater.inflate(R.layout.fragment_matches, container, false)

        // Set up the RecyclerView
        view.matches_recycler_view.setHasFixedSize(true)
        view.matches_recycler_view.layoutManager = LinearLayoutManager(context)

        val matchNameArr = resources.getStringArray(R.array.matchNames)
        val matchImages = resources.obtainTypedArray(R.array.matchImageIds)
        val matchesList = List<MatchEntry>(matchNameArr.size) { index ->
            MatchEntry(matchNameArr[index],
                matchImages.getResourceId(index, -1), "")
        }

        val adapter = MatchCardAdapter(matchesList)
        view.matches_recycler_view.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.small_item_spacing)
        view.matches_recycler_view.addItemDecoration(MatchesItemDecoration(largePadding, smallPadding))
        // Inflate the layout for this fragment
        return view
    }
}
