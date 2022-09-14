package com.smilias.learnit

import com.smilias.learnit.menu_screen.model.VideoInfo

class VideoList {

    companion object{

        fun getVideoList() = listOf<VideoInfo>(
        VideoInfo(
        description = "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.\n\nLicensed under the Creative Commons Attribution license\nhttp://www.bigbuckbunny.org",
        source = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        subtitle = "By Blender Foundation",
        thumb = R.drawable.big_buck_bunny,
        title = "Big Buck Bunny"
        ),
        VideoInfo(
        description = "The first Blender Open Movie from 2006",
        source = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        subtitle = "By Blender Foundation",
        thumb = R.drawable.elephants_dream,
        title = "Elephant Dream"
        ),
        VideoInfo(
        description = "HBO GO now works with Chromecast -- the easiest way to enjoy online video on your TV. For when you want to settle into your Iron Throne to watch the latest episodes. For $35.\nLearn how to use Chromecast with HBO GO and more at google.com/chromecast.",
        source = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        subtitle = "By Google",
        thumb = R.drawable.for_bigger_blazes,
        title = "For Bigger Blazes"
        ),
        VideoInfo(
        description = "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for when Batman's escapes aren't quite big enough. For $35. Learn how to use Chromecast with Google Play Movies and more at google.com/chromecast.",
        source = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
        subtitle = "By Google",
        thumb = R.drawable.for_bigger_escapes,
        title = "For Bigger Escape"
        )
        )
    }
}