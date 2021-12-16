package com.smilias.learnit.image_recognition

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import timber.log.Timber

object ImageRecognition {

    fun imageRecognition(context: Context, uri: Uri): MutableList<String>{

        val resultList= mutableListOf<String>()
        val options = ImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.1f)
            .build()
        val labeler = ImageLabeling.getClient(options)
        val resultText: StringBuilder = StringBuilder()
        val image = InputImage.fromFilePath(context, uri)

        labeler.process(image)
            .addOnSuccessListener { labels ->
                labels.forEachIndexed { index, item ->
                    val text = item.text
                    resultText.append("Item ${index + 1}: $text\n")
                }
                resultList+= resultText.toString()
            }
            .addOnFailureListener { e ->
                Timber.d(e, "test: ")
            }
            .addOnCompleteListener {
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(context.contentResolver, uri)
//                bitmap.value =
//                    ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)

            }
        return resultList
    }

}