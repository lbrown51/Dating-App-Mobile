package com.ad340.datingapp

import android.util.Log
import com.google.firebase.firestore.*

class FirebaseMatchModel {

    private val TAG = "FIREBASE_MATCH_MODEL"
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val listeners: MutableList<ListenerRegistration> = mutableListOf()

    fun getMatches(): CollectionReference {
        return db.collection("matches")
    }

    fun updateMatchLikeStatus(uid: String, liked: Boolean) {
        val matchRef = db.collection("matches").document(uid)
        matchRef
            .update("liked", liked)
            .addOnSuccessListener { Log.d(TAG, "Match successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating match", e) }
    }

}