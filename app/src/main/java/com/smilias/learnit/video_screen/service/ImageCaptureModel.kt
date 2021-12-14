package com.smilias.learnit.video_screen.service

import android.content.Context
import android.widget.Toast
import com.cottacush.android.hiddencam.OnImageCapturedListener
import com.smilias.learnit.video_screen.model.PhotoInfo
import java.io.File

class ImageCaptureModel(val context: Context, val captureInfo: PhotoInfo): OnImageCapturedListener {
    override fun onImageCaptureError(e: Throwable?) {
        Toast.makeText(context, "imaged captured error", Toast.LENGTH_LONG).show()
    }

    override fun onImageCaptured(image: File) {
        val time=captureInfo.exoPlayer.value.contentPosition
        Toast.makeText(context, "imaged captured user: ${captureInfo.user} $time", Toast.LENGTH_LONG).show()

    }
}