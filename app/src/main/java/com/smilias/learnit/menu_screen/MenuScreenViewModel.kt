package com.smilias.learnit.menu_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.smilias.learnit.R
import com.smilias.learnit.menu_screen.model.VideoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {



}