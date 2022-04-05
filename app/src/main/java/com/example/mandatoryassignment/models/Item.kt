package com.example.mandatoryassignment.models

import android.icu.text.SimpleDateFormat
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable
import java.sql.Timestamp
import java.util.*

data class Item(val id: Int, val title: String, val description: String, val price: Int, val seller: String, val date: Int){


    override fun toString(): String {
//        val stamp = Timestamp(System.currentTimeMillis())
//        val actualDate = Date(stamp.time)
        val format = SimpleDateFormat.getDateTimeInstance()
        val actualDate = format.format(date * 1000L)


        return "$id, $title, $description, $price, $seller, $actualDate"
    }
}