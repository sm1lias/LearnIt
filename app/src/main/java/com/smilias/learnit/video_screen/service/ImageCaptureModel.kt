package com.smilias.learnit.video_screen.service

import android.content.Context
import android.widget.Toast
import androidx.core.net.toUri
import com.cottacush.android.hiddencam.OnImageCapturedListener
import com.smilias.learnit.image_recognition.ImageRecognition
import com.smilias.learnit.firebase.FirebaseData
import com.smilias.learnit.firebase.FirebaseJobs
import com.smilias.learnit.video_screen.model.PhotoInfo
import java.io.File

class ImageCaptureModel(val context: Context, private val captureInfo: PhotoInfo): OnImageCapturedListener {
    private val firebaseData: FirebaseData = FirebaseData(captureInfo.user)
    private val imageRecognition = ImageRecognition
    override fun onImageCaptureError(e: Throwable?) {
        Toast.makeText(context, "imaged captured error", Toast.LENGTH_LONG).show()
    }

    override fun onImageCaptured(image: File) {

        var videoTime=captureInfo.exoPlayer.value.contentPosition
        Toast.makeText(context, "imaged captured user: ${captureInfo.user} $videoTime", Toast.LENGTH_LONG).show()
        val uri= File(image.absolutePath).toUri()



        firebaseData.firebaseInfo.time=videoTime
        imageRecognition.imageRecognition(context, uri, firebaseData)




    }
}