package com.smilias.learnit.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.IOException

object Utils {

    fun createBaseFolder(context:Context): File{
        val baseStorageFolder = File(context.getExternalFilesDir(null), "HiddenCam").apply {
            if (exists()) deleteRecursively()
            mkdir()
        }
        return baseStorageFolder
    }
}