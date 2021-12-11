package com.smilias.learnit.video_screen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.smilias.learnit.BuildConfig
import com.smilias.learnit.utils.Utils.createImageFile
import com.smilias.learnit.utils.Utils.saveBitmap
import com.smilias.learnit.utils.foregroundStartService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber


@Composable
fun VideoScreen(
    sourceUrl: String = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    viewModel: VideoScreenViewModel = hiltViewModel()
) {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    val launcherForPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Timber.d("PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Timber.d("PERMISSION DENIED")
        }
    }


    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        viewModel.exoPlayer
    }

    // We only want to react to changes in sourceUrl.
    // This effect will be executed at each commit phase if
    // [sourceUrl] has changed.
    LaunchedEffect(sourceUrl) {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(
                Uri.parse(
                    // Big Buck Bunny from Blender Project
                    sourceUrl
                )
            )

        exoPlayer.value.prepare(source)
    }
    val file = createImageFile(context)!!
    var uri: Uri = FileProvider.getUriForFile(
        context, BuildConfig.APPLICATION_ID + ".provider",
        file
    )
    val coroutineScope = rememberCoroutineScope()
    val photoLauncher = rememberLauncherForActivityResult(CaptureImageNoDialog()) {
        if (it) {
            coroutineScope.launch {
                var source: ImageDecoder.Source =
                    ImageDecoder.createSource(context.contentResolver, uri)
                val bitmap = ImageDecoder.decodeBitmap(source)
                saveBitmap(context, bitmap, Bitmap.CompressFormat.JPEG, "image/jpeg", "temp")


            }


        }
    }
    LaunchedEffect(viewModel.playing) {
        if (viewModel.playing) {
            context.foregroundStartService("Start")
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) -> {
                    photoLauncher.launch(uri)
                }
                else -> {
                    // Asking for permission
                    launcherForPermission.launch(Manifest.permission.CAMERA)
                }
            }
        } else {
            context.foregroundStartService("Exit")
        }
    }

    // Gateway to traditional Android Views
    AndroidView(factory = { context ->
        PlayerView(context).apply {
            player = exoPlayer.value
//                playWhenReady = true
        }
    }
    )
}
