package com.smilias.learnit.firebase

import com.google.firebase.auth.FirebaseUser

data class FirebaseData(val user: FirebaseUser?=null, val firebaseInfo: FirebaseInfo=FirebaseInfo())
