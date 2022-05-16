package com.wadachirebandi.kiddietracking.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserDao {

    private val db = Firebase.firestore
    val userCollection = db.collection("Users")
    private val auth = Firebase.auth

    fun getUser(): Task<DocumentSnapshot> {
        val currentUser = auth.currentUser!!.uid
        return userCollection.document(currentUser).get()
    }
}