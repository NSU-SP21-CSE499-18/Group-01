package com.ece.nsu.spring2021.cse499.arschoolbook.models

/**
 * model class for a single YouTube video
 */
data class YouTubeVideo(var link: String? = "", var source: String? = "", var videoId: String? = ""){

    // for static method
    companion object{

        fun generateDummyYouTubeVideos(): ArrayList<YouTubeVideo> {

            val dummyYoutubeVideos = ArrayList<YouTubeVideo>()

            dummyYoutubeVideos.add(
                YouTubeVideo(link = "https://www.youtube.com/watch?v=1XZrh83Qq9I",
                    source = "10 Minutes School", videoId = "1XZrh83Qq9I")
            )
            dummyYoutubeVideos.add(
                YouTubeVideo(link = "https://www.youtube.com/watch?v=GM2paTI5D9A",
                    source = "Technic Easy Education", videoId = "GM2paTI5D9A")
            )
            dummyYoutubeVideos.add(
                YouTubeVideo(link = "https://www.youtube.com/watch?v=4NKpbTRq36k",
                    source = "Technic Easy Education", videoId = "4NKpbTRq36k")
            )

            return dummyYoutubeVideos
        }

    }
}
