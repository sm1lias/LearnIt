package com.smilias.learnit.utils

import android.content.Context
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object Utils {

    fun createBaseFolder(context:Context): File{
        val baseStorageFolder = File(context.getExternalFilesDir(null), "HiddenCam").apply {
            if (exists()) deleteRecursively()
            mkdir()
        }
        return baseStorageFolder
    }

    fun getDateFromMillis(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        val instant = Instant.ofEpochMilli(System.currentTimeMillis())
        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return formatter.format(date).replace("/", "-")

    }

    fun getTimeFromMillis(millis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("mm:ss.SSS")
        val instant = Instant.ofEpochMilli(millis)
        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return formatter.format(date)
    }
}