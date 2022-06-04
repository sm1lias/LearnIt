package com.smilias.learnit.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smilias.learnit.utils.Utils

object FirebaseJobs {
    private lateinit var database: DatabaseReference

    fun write(firebaseData: FirebaseData){
        database = Firebase.database.reference

        database.child("users").child(firebaseData.user!!.uid).child(Utils.getDateFromMillis()).setValue(firebaseData.firebaseInfo)
    }
}