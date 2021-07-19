package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.models.YouTubeVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * Activity Class for a single chapter on book
 */
class ContentActivity : AppCompatActivity(), YouTubePlayerCallback {

    // ui
    private lateinit var backButton : ImageButton
    private lateinit var chapterNoTv: TextView
    private lateinit var chapterNameTv: TextView
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button

    // models
    private var chapterNo: String = ""
    private var chapterName: String = ""
    private var mYoutubeVideos = ArrayList<YouTubeVideo>()
    private var currentVideoPosition = -1

    // youtube api
    private lateinit var mYouTubePlayerView: YouTubePlayerView
    private var mYouTubePlayer: YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        mYouTubePlayerView.release()
    }

    private fun init() {

        // initialize UI components
        backButton = findViewById(R.id.backBtn)
        chapterNoTv = findViewById(R.id.selected_ch_no)
        chapterNameTv = findViewById(R.id.selected_ch_name)
        nextButton = findViewById(R.id.nextBtn)
        previousButton = findViewById(R.id.prevBtn)

        // get data for the UI
        val bundle: Bundle? = intent.extras
        chapterNo = bundle?.get("Chapter-No").toString()
        chapterName = bundle?.get("Chapter-Name").toString()

        // set data to UI
        chapterNoTv.text = chapterNo
        chapterNameTv.text = chapterName

        // youtube api initialization
        mYouTubePlayerView = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(mYouTubePlayerView)
        mYouTubePlayerView.getYouTubePlayerWhenReady(this)
    }

    /**
     * back button press listener
     */
    fun backClick(view: View) {
        finish()
    }

    fun nextButtonClick(view: View) {

        if(mYoutubeVideos.size==0) return

        currentVideoPosition++
        currentVideoPosition %= mYoutubeVideos.size

        playVideoAtPosition(currentVideoPosition)
    }

    fun previousButtonClick(view: View) {

        if(mYoutubeVideos.size==0) return

        currentVideoPosition--
        if (currentVideoPosition<0) currentVideoPosition += mYoutubeVideos.size

        playVideoAtPosition(currentVideoPosition)
    }

    /**
     * listener for when youtube player is ready to play the video
     */
    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {

        mYouTubePlayer = youTubePlayer

        enableVideoPlayerUi()

        fetchYoutubeVideosFromDatabase(chapterNo)
    }

    /**
     * enable all ui components relevant to video playing
     * such as- next, previous button
     */
    private fun enableVideoPlayerUi() {

        nextButton.isEnabled = true
        previousButton.isEnabled = true
    }

    /**
     * start reading youtube videos for the chapter from database
     * @param chapterNo id of the chapter
     */
    private fun fetchYoutubeVideosFromDatabase(chapterNo: String) {
        // TODO: fetch from firebase realtime db

        // fetching dummy video for now
        mYoutubeVideos = YouTubeVideo.generateDummyYouTubeVideos()
        currentVideoPosition = 0
        playVideoAtPosition(currentVideoPosition)
    }

    /**
     * play video in the Arraylist at a particular position
     * @param videoPosition index of the video
     */
    private fun playVideoAtPosition(videoPosition: Int) {

        if(videoPosition<0 || videoPosition>=mYoutubeVideos.size || mYouTubePlayer==null) return

        mYouTubePlayer!!.loadVideo(mYoutubeVideos.get(videoPosition).videoId!!, 0F)
        mYouTubePlayer!!.pause()
    }
}