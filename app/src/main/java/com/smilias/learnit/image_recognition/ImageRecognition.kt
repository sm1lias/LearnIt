package com.smilias.learnit.image_recognition

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.smilias.learnit.firebase.FirebaseData
import com.smilias.learnit.firebase.FirebaseJobs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

object ImageRecognition {


    fun imageRecognition(context: Context, uri: Uri, firebaseData: FirebaseData){
        val resultList= mutableListOf<String>()
        val options = ImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.6f)
            .build()
        val labeler = ImageLabeling.getClient(options)
        val image = InputImage.fromFilePath(context, uri)
            labeler.process(image)
                .addOnSuccessListener { labels ->
                    labels.forEachIndexed { index, item ->
                        val text = item.text
                        if (!resultList.contains("Item ${index + 1}: $text")) {
                            resultList.add("Item ${index + 1}: $text")
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Timber.d(e, "test: ")
                }
                .addOnCompleteListener {


                    firebaseData.firebaseInfo.data= resultList
                    FirebaseJobs.write(firebaseData)

                }

    }

}