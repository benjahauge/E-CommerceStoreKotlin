package com.example.mandatoryassignment.models

import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class Item(val id: Int, val title: String, val description: String, val price: Int, val seller: String, val date: Int){
    //constructor(title: String, description: String, price: Int, seller: String, date: Int) : this(-1, title, description, price, seller, date)


    override fun toString(): String {
        return "$id, $title, $description, $price, $seller, $date"
    }
}