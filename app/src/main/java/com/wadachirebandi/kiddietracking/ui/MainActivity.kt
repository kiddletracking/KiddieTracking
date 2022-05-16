package com.wadachirebandi.kiddietracking.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.wadachirebandi.kiddietracking.R

class MainActivity : AppCompatActivity() {

    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            token = it
            Log.i("TOKEN", token)
        }

        FirebaseMessaging.getInstance().subscribeToTopic(token).addOnCompleteListener {
            Log.i("TOKEN", "Subscribe successfully")
        }
    }
}