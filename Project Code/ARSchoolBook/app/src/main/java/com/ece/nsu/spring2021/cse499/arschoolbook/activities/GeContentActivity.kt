package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import android.content.Intent
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.models.YouTubeVideo
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ResourceFetcherUtil
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.sharedPreferences.UserChoiceSharedPref
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.*
import kotlin.collections.ArrayList


/**
 * Activity Class for a single chapter on book
 */
class GeContentActivity : AppCompatActivity(), YouTubePlayerCallback {

    private val TAG: String = "CA-debug"
    private val REQUEST_CODE_FOR_SPEECH = 200

    // ui
    private lateinit var backButton : ImageButton
    private lateinit var chapterNoTv: TextView
    private lateinit var chapterNameTv: TextView
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var searchET: TextInputEditText
    private lateinit var micButton : ImageButton
    private lateinit var clearButton : ImageButton

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
        slideInRightOutLeft()
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
        micButton = findViewById(R.id.micBtn)
        clearButton = findViewById(R.id.clearBtn)

        // get data for the UI
        val index = intent.getIntExtra("SelectedChapter",0)
        chapterNo = resources.getStringArray(R.array.chapter_numbers)[index]

        //If SELECTED_CLASS == 7 (skipping conditions for now)
        chapterName = resources.getStringArray(R.array.chapter_names_c7)[index]

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
                val keyword = searchET.text
                performSearch(keyword)
                return@OnEditorActionListener true
            }
            false
        })

        //Handling keyword (input) text watcher
        searchET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if(s.isNotEmpty()) {
                        micButton.visibility = View.GONE
                        clearButton.visibility = View.VISIBLE
                    } else {
                        micButton.visibility = View.VISIBLE
                        clearButton.visibility = View.GONE
                    }
                }
            }
        })
    }

    //Speech input callback
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_FOR_SPEECH -> {
                if (resultCode == RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    searchET.setText(result!![0])
                    searchET.requestFocus()
                    searchET.setSelection(searchET.length())
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(searchET, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        }
    }


    fun backClick(view: View) {
        finish()
        slideInLeftOutRight()
    }

    fun clearSearchBox(view: View)
    {
        searchET.setText("")
        clearButton.visibility = View.GONE
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

        val userSelectedClassName = UserChoiceSharedPref.build(this)
            .getSelectedClassName(resources.getString(R.string.cl_7))

        Log.d(TAG, "init: user selected class = "+userSelectedClassName)

        val dbPath = ResourceFetcherUtil.getDbPathForChapterAndClass(className = userSelectedClassName,
            chapterNo = chapterNo, this)

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

    private fun slideInLeftOutRight() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun performSearch(keyword: Editable?)
    {
        val url = "https://www.google.com/search?q=$keyword"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
        searchET.setText("")
    }

    fun speechInput(view : View) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        val lang = getResources().getConfiguration().getLocales().get(0).toString()
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Try saying some search keywords")
        try {
            startActivityForResult(intent,REQUEST_CODE_FOR_SPEECH)
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun viewFiguresClick(view :View)
    {
        val intent = Intent(this, SelectFigureActivity::class.java)
        intent.putExtra("Chapter-Name",chapterName)
        intent.putExtra("Chapter-No",chapterNo)
        startActivity(intent)
        slideInRightOutLeft()
    }
    private fun slideInRightOutLeft() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onBackPressed() {
        finish()
        slideInLeftOutRight()

    }
}