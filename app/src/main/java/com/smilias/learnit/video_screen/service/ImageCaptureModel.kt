package com.smilias.learnit.video_screen.service

import android.content.Context
import android.widget.Toast
import com.cottacush.android.hiddencam.OnImageCapturedListener
import java.io.File

class ImageCaptureModel(val context: Context): OnImageCapturedListener {
    override fun onImageCaptureError(e: Throwable?) {
        Toast.makeText(context, "imaged captured error", Toast.LENGTH_LONG).show()
    }

    override fun onImageCaptured(image: File) {
        Toast.makeText(context, "imaged captured ${image.absolutePath}", Toast.LENGTH_LONG).show()

    }
}