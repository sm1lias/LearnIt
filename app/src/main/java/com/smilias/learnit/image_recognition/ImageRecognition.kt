package com.smilias.learnit.image_recognition

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.widget.Toast
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.smilias.learnit.firebase.FirebaseData
import com.smilias.learnit.firebase.FirebaseJobs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

object ImageRecognition {

    val resultList= mutableListOf<String>()


    fun imageRecognition(context: Context, uri: Uri, firebaseData: FirebaseData){
//        val resultList= mutableListOf<String>()
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
                    faceDetection(uri, context, firebaseData)

                }
                .addOnFailureListener { e ->
                    Timber.d(e, "test: ")
                }
                .addOnCompleteListener {




                }

    }

    private fun faceDetection(uri: Uri, context: Context, firebaseData: FirebaseData) {
        val image = InputImage.fromFilePath(context, uri)
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(highAccuracyOpts)

        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isEmpty()) {
                    Toast.makeText(context,"Nothing found", Toast.LENGTH_SHORT)
                } else {
//                    val source: ImageDecoder.Source =
//                        ImageDecoder.createSource(context.contentResolver, uri)
//                    bitmap.value =
//                        ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
                    Toast.makeText(context,"face found", Toast.LENGTH_SHORT)

                    faceProcess(faces, firebaseData)

                }
            }
            .addOnFailureListener { e ->
            }

    }

    private fun faceProcess(faces: List<Face>, firebaseData: FirebaseData) {
        val paint = Paint()
        paint.apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }

//        val canvas = Canvas(bitmap)
        for (face in faces) {
            val bounds = face.boundingBox
//            canvas.drawRect(bounds, paint)
            val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
            val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees


//             If landmark detection was enabled (mouth, ears, eyes, cheeks, and
//             nose available):
            val leftEar = face.getLandmark(FaceLandmark.LEFT_EAR)
            leftEar?.let {
                val leftEarPos = leftEar.position
            }

            // If contour detection was enabled:
            val leftEyeContour = face.getContour(FaceContour.LEFT_EYE)?.points
            val upperLipBottomContour = face.getContour(FaceContour.UPPER_LIP_BOTTOM)?.points

            // If classification was enabled:
            if (face.smilingProbability >0.5f) {
                resultList.add("smiling")
                val smileProb = face.smilingProbability
            }
            if (face.rightEyeOpenProbability >0.5f) resultList.add("left eye opened") else resultList.add("left eye closed")

//            if (face.rightEyeOpenProbability >0.5f) {
//                resultList.add("left eye opened")
////                val rightEyeOpenProb = face.rightEyeOpenProbability
//            }
            if (face.leftEyeOpenProbability >0.5f) resultList.add("right eye opened") else resultList.add("right eye closed")
//            if (face.leftEyeOpenProbability >0.5f) {
//                resultList.add("right eye opened")
////                val rightEyeOpenProb = face.rightEyeOpenProbability
//            } else {
//                resultList
//            }

            // If face tracking was enabled:
            if (face.trackingId != null) {
                val id = face.trackingId
            }
        }
        firebaseData.firebaseInfo.data= resultList
        FirebaseJobs.write(firebaseData)
        resultList.clear()
//        this.bitmap.value = bitmap

    }

}