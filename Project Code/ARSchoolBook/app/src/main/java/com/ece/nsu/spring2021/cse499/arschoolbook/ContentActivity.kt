package com.ece.nsu.spring2021.cse499.arschoolbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * Activity Class for a single chapter on book
 */
class ContentActivity : AppCompatActivity(), YouTubePlayerFullScreenListener {

    // ui
    private lateinit var backButton : ImageButton
    private lateinit var chapterNoTv: TextView
    private lateinit var chapterNameTv: TextView

    // models
    private var chapterNo: String = ""
    private var chapterName: String = ""

    // youtube api
    private lateinit var youtubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        init()
    }

    private fun init() {

        // initialize UI components
        backButton = findViewById(R.id.backBtn)
        chapterNoTv = findViewById(R.id.selected_ch_no)
        chapterNameTv = findViewById(R.id.selected_ch_name)

        // get data for the UI
        val bundle: Bundle? = intent.extras
        chapterNo = bundle?.get("Chapter-No").toString()
        chapterName = bundle?.get("Chapter-Name").toString()

        // set data to UI
        chapterNoTv.text = chapterNo
        chapterNameTv.text = chapterName

        // youtube api initialization
        youtubePlayerView = findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youtubePlayerView)
        youtubePlayerView.addFullScreenListener(this)
    }

    /**
     * back button press listener
     */
    fun backClick(view: View) {
        finish()
    }

    /**
     * listener for when youtube video enters full screen mode
     */
    override fun onYouTubePlayerEnterFullScreen() {
        // TODO: implement
    }

    /**
     * listener for when youtube video exits full screen mode
     */
    override fun onYouTubePlayerExitFullScreen() {
        // TODO: implement
    }
}