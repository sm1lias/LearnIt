package com.smilias.learnit.firebase

import com.smilias.learnit.menu_screen.LocationDetails

data class FirebaseInfo(var title: String = "", var data: MutableList<String>? =null, var time:String="", var location: LocationDetails=LocationDetails())
