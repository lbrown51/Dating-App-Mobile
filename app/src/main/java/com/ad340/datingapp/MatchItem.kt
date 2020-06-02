package com.ad340.datingapp

import java.io.Serializable

data class MatchItem(
    val uid: String,
    val name: String,
    val imageUrl: String,
//    val lat: String,
//    val longitude: String,
    val liked: Boolean
): Serializable {

    constructor(): this(
        "", "","", false
    )

}