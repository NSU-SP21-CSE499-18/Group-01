package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.models.YouTubeVideo
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.NosqlDbPathUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


/**
 * Activity Class for a single chapter on book
 */
class ContentActivity : AppCompatActivity(), YouTubePlayerCallback {

    private val TAG: String = "CA-debug"

    // ui
    private lateinit var backButton : ImageButton
    private lateinit var chapterNoTv: TextView
    private lateinit var chapterNameTv: TextView
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var searchET: TextInputEditText

    // models
    private var chapterNo: String = ""
    private var chapterName: String = ""
    private var mYoutubeVideos = ArrayList<YouTubeVideo>()
    private var currentVideoPosition = -1

    // youtube api
    private lateinit var mYouTubePlayerView: YouTubePlayerView
    private var mYouTubePlayer: YouTubePlayer? = null

    // firebase database variables
    private lateinit var firebaseDb: DatabaseReference

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
        searchET = findViewById(R.id.searchBox)

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


        //Handling Text Input Edit Text (search box)
        searchET.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var keyword = searchET.text
                performSearch(keyword)
                return@OnEditorActionListener true
            }
            false
        })
    }

    /**
     * back button press listener
     */
    fun backClick(view: View) {
        finish()
        slideInLeftOutRight()
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

        firebaseDb = FirebaseDatabase.getInstance().reference

        var dbPath: String = NosqlDbPathUtils.CLASS_7_BOOK_NODE + "/"
        when(chapterNo){
            getString(R.string.ch_no_1) -> dbPath += NosqlDbPathUtils.CHAPTER_1_NODE + "/"
            getString(R.string.ch_no_2) -> dbPath += NosqlDbPathUtils.CHAPTER_2_NODE + "/"
            getString(R.string.ch_no_3) -> dbPath += NosqlDbPathUtils.CHAPTER_3_NODE + "/"
            getString(R.string.ch_no_4) -> dbPath += NosqlDbPathUtils.CHAPTER_4_NODE + "/"
            getString(R.string.ch_no_5) -> dbPath += NosqlDbPathUtils.CHAPTER_5_NODE + "/"
            getString(R.string.ch_no_6) -> dbPath += NosqlDbPathUtils.CHAPTER_6_NODE + "/"
            getString(R.string.ch_no_7) -> dbPath += NosqlDbPathUtils.CHAPTER_7_NODE + "/"
            getString(R.string.ch_no_8) -> dbPath += NosqlDbPathUtils.CHAPTER_8_NODE + "/"
            getString(R.string.ch_no_9) -> dbPath += NosqlDbPathUtils.CHAPTER_9_NODE + "/"
            getString(R.string.ch_no_10) -> dbPath += NosqlDbPathUtils.CHAPTER_10_NODE + "/"
            getString(R.string.ch_no_11) -> dbPath += NosqlDbPathUtils.CHAPTER_11_NODE + "/"
            getString(R.string.ch_no_12) -> dbPath += NosqlDbPathUtils.CHAPTER_12_NODE + "/"
            getString(R.string.ch_no_13) -> dbPath += NosqlDbPathUtils.CHAPTER_13_NODE + "/"
            getString(R.string.ch_no_14) -> dbPath += NosqlDbPathUtils.CHAPTER_14_NODE + "/"
        }
        dbPath += NosqlDbPathUtils.YOUTUBE_VIDEOS_NODE + "/"

        firebaseDb.child(dbPath).get().addOnSuccessListener {
            for(snap in it.children) {
                val youtubeVideo = snap.getValue(YouTubeVideo::class.java)
                if (youtubeVideo != null) {
                    mYoutubeVideos.add(youtubeVideo)
                    if (mYoutubeVideos.size == 1) {
                        currentVideoPosition = 0
                        playVideoAtPosition(currentVideoPosition)
                    }
                }
            }
        }.addOnFailureListener{
            Log.d(TAG, "fetchYoutubeVideosFromDatabase: error = " + it.message)
        }
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

    fun slideInLeftOutRight() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun performSearch(keyword: Editable?)
    {
        val url = "https://www.google.com/search?q=$keyword"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}