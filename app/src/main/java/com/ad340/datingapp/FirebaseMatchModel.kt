package com.ad340.datingapp

import android.util.Log
import com.google.firebase.firestore.*

class FirebaseMatchModel {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val listeners: MutableList<ListenerRegistration> = mutableListOf()

    fun getMatches(): CollectionReference {
        return db.collection("matches")
    }

}