package com.ad340.datingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

        val adapter = MatchCardAdapter(context!!)
        view.matches_recycler_view.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.small_item_spacing)
        view.matches_recycler_view.addItemDecoration(MatchesItemDecoration(largePadding, smallPadding))

        val firebaseMatchViewModel = ViewModelProvider(this)[FirebaseMatchViewModel::class.java]
        firebaseMatchViewModel.getMatches().observe(this, Observer { matchList ->
            matchList?.let { adapter.setMatchList(it) }
        })

//        val matchNameArr = resources.getStringArray(R.array.matchNames)
//        val matchImages = resources.obtainTypedArray(R.array.matchImageIds)
//
//        val matchesList = List<MatchEntry>(matchNameArr.size) { index ->
//            MatchEntry(matchNameArr[index],
//                matchImages.getResourceId(index, -1), "")
//        }

        // Inflate the layout for this fragment
        return view
    }
}
