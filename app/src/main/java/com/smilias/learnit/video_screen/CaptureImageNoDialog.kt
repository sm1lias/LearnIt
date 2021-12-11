package com.smilias.learnit.video_screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts

class CaptureImageNoDialog : ActivityResultContracts.TakePicture() {

    override fun createIntent(context: Context, input: Uri): Intent {

        return super.createIntent(context, input)
            .putExtra("android.intent.extra.quickCapture", true)
    }
}