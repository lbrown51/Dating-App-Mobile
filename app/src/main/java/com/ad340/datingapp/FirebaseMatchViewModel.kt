package com.ad340.datingapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot

class FirebaseMatchViewModel: ViewModel() {

    val TAG = "FIRESTORE_MATCH_VIEW_MODEL"
    private val matchModel: FirebaseMatchModel = FirebaseMatchModel()
    private val matches: MutableLiveData<List<MatchItem>> = MutableLiveData()

    fun getMatches(): LiveData<List<MatchItem>> {
        matchModel.getMatches()
            .addSnapshotListener(EventListener<QuerySnapshot> { querySnapshot, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    matches.value = null
                    return@EventListener
                }

                val matchList: MutableList<MatchItem> = mutableListOf()
                for (doc in querySnapshot!!) {
                    val item = doc.toObject(MatchItem::class.java)
                    matchList.add(item)
                }

                matches.value = matchList
            })

        return matches
    }
}