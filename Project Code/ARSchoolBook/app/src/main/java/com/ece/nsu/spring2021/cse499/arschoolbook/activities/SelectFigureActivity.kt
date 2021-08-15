package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.adpters.SelectFigureAdapter
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ArUtil
import com.google.ar.core.ArCoreApk

class SelectFigureActivity : AppCompatActivity(), SelectFigureAdapter.SelectFigureAdapterCallback {

    private var TAG: String = "SFA-debug"

    private var chapterNo: String = ""
    private var chapterName: String = ""
    private lateinit var SELECTED_CHAPTER: String
    lateinit var data: Array<String>

    //UI
    private lateinit var chapterNoTv: TextView
    private lateinit var chapterNameTv: TextView
    private lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_figure)
        init()
    }

    private fun init()
    {
        // get data for the UI
        val bundle: Bundle? = intent.extras
        chapterNo = bundle?.get("Chapter-No").toString()
        chapterName = bundle?.get("Chapter-Name").toString()
        SELECTED_CHAPTER = chapterNo

        //Init UI components
        chapterNoTv = findViewById(R.id.selected_ch_no_sf)
        chapterNameTv = findViewById(R.id.selected_ch_name_sf)
        recyclerview = findViewById<RecyclerView>(R.id.recycler_view)


        // set data to UI
        chapterNoTv.text = chapterNo
        chapterNameTv.text = chapterName

        //Recycler View
        recyclerview.layoutManager = LinearLayoutManager(this)
        data = getFigureNameListFromSelectedChapter(SELECTED_CHAPTER)
        val adapter = SelectFigureAdapter(data, this)
        recyclerview.adapter = adapter

    }

    /**
     * view figures onClick listener
     */
     fun scanFiguresClick(view: View) {
        startActivity(Intent(this, AugmentedImagesActivity::class.java))
    }

    fun backClick(view: View) {
        finish()
        slideInLeftOutRight()
    }

    /**
     * onClick listener for SelectFigureRecyclerview items
     */
    override fun onFigureItemClick(figureName: String) {
        Log.d(TAG, "onFigureItemClick: clicked on figure item = $figureName")

        showFigure3dModel(figureName)
    }

    /**
     * check for AR availability and open 3D view or AR view accordingly
     */
    private fun showFigure3dModel(figureName: String) {
        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (availability.isTransient) {
            // Continue to query availability at 5Hz while compatibility is checked in the background.
            Handler().postDelayed({
                showFigure3dModel(figureName)
            }, 200)
        }

        if (availability.isSupported) {
            Log.d(TAG, "showFigure3dModel: device supports AR")

            showFigureInArView(figureName)
        }

        else {
            Log.d(TAG, "showFigure3dModel: device does not support AR")

            showFigureIn3dView(figureName)
        }
    }

    private fun showFigureInArView(figureName: String) {
        // open Ar view -> ViewSelectedArModelActivity

        val intent = Intent(this, ViewSelectedArModelActivity::class.java)
        intent.putExtra("Figure-Name", figureName)
        startActivity(intent)
    }

    private fun showFigureIn3dView(figureName: String) {
        // open 3d view

        val modelUrl = getModelUrlBasedOnFigureName(figureName)

        if(modelUrl==null || modelUrl.isEmpty()) {
            Log.d(TAG, "showFigureIn3dView: modelUrl not found for figure=$figureName")
            showToast("Sorry, figure not available")
            return
        }

        Log.d(TAG, "showFigureIn3dView: model url = "+modelUrl)

        val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
        val intentUri: Uri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
            .appendQueryParameter(
                "file",
                modelUrl
            )
            .appendQueryParameter("mode", "3d_only")
            .build()
        sceneViewerIntent.data = intentUri
        sceneViewerIntent.setPackage("com.google.ar.core")
        startActivity(sceneViewerIntent)
    }

    private fun getModelUrlBasedOnFigureName(figureName: String): String? {

        when(figureName){

            getString(R.string.fig_no_1_1) -> return ArUtil.VIRUS_MODEL_URL

            getString(R.string.fig_no_2_1a) -> return ArUtil.PLANT_CELL_MODEL_URL

            getString(R.string.fig_no_2_1b) -> return ArUtil.ANIMAL_CELL_MODEL_URL

            getString(R.string.fig_no_2_6) -> return ArUtil.NEURON_MODEL_URL

            getString(R.string.fig_no_4_3) -> return ArUtil.LUNG_MODEL_URL

            getString(R.string.fig_no_5_1) -> return ArUtil.DIGESTIVE_MODEL_URL

            getString(R.string.fig_no_12_1) -> return ArUtil.SOLAR_SYSTEM_MODEL_URL
        }

        return null
    }

    private fun slideInLeftOutRight() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun getFigureNameListFromSelectedChapter(chapterNo: String): Array<String> {

        when (chapterNo) {
            "Chapter 1","অধ্যায় ১" -> {
                return resources.getStringArray(R.array.fig_ch_1)
            }
            "Chapter 2","অধ্যায় ২" -> {
                return resources.getStringArray(R.array.fig_ch_2)
            }
            "Chapter 3","অধ্যায় ৩" -> {
                return resources.getStringArray(R.array.fig_ch_3)
            }
            "Chapter 4","অধ্যায় ৪" -> {
                return resources.getStringArray(R.array.fig_ch_4)
            }
            "Chapter 5", "অধ্যায় ৫" -> {
                return resources.getStringArray(R.array.fig_ch_5)
            }
            "Chapter 6", "অধ্যায় ৬" -> {
                return resources.getStringArray(R.array.fig_ch_6)
            }
            "Chapter 7","অধ্যায় ৭" -> {
                return resources.getStringArray(R.array.fig_ch_7)
            }
            "Chapter 8","অধ্যায় ৮" -> {
                return resources.getStringArray(R.array.fig_ch_8)
            }
            "Chapter 9","অধ্যায় ৯" -> {
                return resources.getStringArray(R.array.fig_ch_9)
            }
            "Chapter 10","অধ্যায় ১০" -> {
                return resources.getStringArray(R.array.fig_ch_10)
            }
            "Chapter 11","অধ্যায় ১১" -> {
                return resources.getStringArray(R.array.fig_ch_11)
            }
            "Chapter 12", "অধ্যায় ১২" -> {
                return resources.getStringArray(R.array.fig_ch_12)
            }
            "Chapter 13", "অধ্যায় ১৩" -> {
                return resources.getStringArray(R.array.fig_ch_13)
            }
            "Chapter 14", "অধ্যায় ১৪" -> {
                return resources.getStringArray(R.array.fig_ch_14)
            }
            else -> return emptyArray()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}