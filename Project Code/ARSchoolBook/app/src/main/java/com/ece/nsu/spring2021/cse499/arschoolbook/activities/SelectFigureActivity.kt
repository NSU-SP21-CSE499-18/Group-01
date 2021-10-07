package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.adpters.SelectFigureAdapter
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ArUtil
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ResourceFetcherUtil
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.sharedPreferences.UserChoiceSharedPref
import com.google.ar.core.ArCoreApk

class SelectFigureActivity : AppCompatActivity(), SelectFigureAdapter.SelectFigureAdapterCallback {

    private var TAG: String = "SFA-debug"

    private var chapterNo: String = ""
    private var chapterName: String = ""
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

        //Init UI components
        chapterNoTv = findViewById(R.id.selected_ch_no_sf)
        chapterNameTv = findViewById(R.id.selected_ch_name_sf)
        recyclerview = findViewById<RecyclerView>(R.id.recycler_view)


        // set data to UI
        chapterNoTv.text = chapterNo
        chapterNameTv.text = chapterName

        //Recycler View
        recyclerview.layoutManager = LinearLayoutManager(this)

        val userSelectedClassName = UserChoiceSharedPref.build(this)
            .getSelectedClassName(getString(R.string.cl_7))

        Log.d(TAG, "init: user selected class = "+userSelectedClassName)

        data = ResourceFetcherUtil.getFigureNameListFromSelectedClassAndChapter(
            className = userSelectedClassName, chapterNo = chapterNo, this)

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

        val modelUrl = ResourceFetcherUtil.getModelUrlBasedOnFigureName(figureName, this)

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

    private fun slideInLeftOutRight() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        finish()
        slideInLeftOutRight()
    }
}