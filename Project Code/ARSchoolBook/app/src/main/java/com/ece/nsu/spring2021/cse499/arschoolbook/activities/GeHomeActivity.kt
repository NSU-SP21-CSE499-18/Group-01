package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import belka.us.androidtoggleswitch.widgets.ToggleSwitch
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.adpters.HomeAdapter
import com.ece.nsu.spring2021.cse499.arschoolbook.utils.ResourceFetcherUtil
import java.util.*


class GeHomeActivity : AppCompatActivity() {
    private val TAG: String = "GHA-debug"
    lateinit var chapterNumbers: Array<String>
    lateinit var chapterNames: Array<String>
    lateinit var SELECTED_CLASS: String
    lateinit var classNameTV: TextView

    var CURRENT_SELECTED_LANGUAGE = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ge_home)
        val toggleSwitch : ToggleSwitch = findViewById(R.id.lng_switch_ge)
        val bundle: Bundle? = intent.extras
        val language = bundle?.get("lng")

        if(language!=null)
        {
            if(language == "bn") toggleSwitch.checkedTogglePosition = 1
            else toggleSwitch.checkedTogglePosition = 0

            CURRENT_SELECTED_LANGUAGE = language.toString()
        }
        else{
            slideInRightOutLeft()
        }


        toggleSwitch.setOnToggleSwitchChangeListener { position, isChecked ->
            if(position==1) setLang("bn")
            else setLang("en")
        }

        init()
    }

    fun init()
    {
        classNameTV = findViewById(R.id.className_home)
        SELECTED_CLASS = intent.getStringExtra("SelectedClass").toString()
        classNameTV.text = ResourceFetcherUtil.getClassNameTitle(SELECTED_CLASS, this)
        chapterNames = ResourceFetcherUtil.getChapterNamesFromSelectedClass(SELECTED_CLASS, this)
        chapterNumbers = resources.getStringArray(R.array.chapter_numbers)

        val recyclerview = findViewById<RecyclerView>(R.id.recycler_view_chapters)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = HomeAdapter(chapterNumbers,chapterNames,this)
        recyclerview.adapter = adapter
    }

    private fun setLocale(activity: Activity, languageCode: String? ) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.getConfiguration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }

    private fun setLang(lang: String?)
    {
        // do nothing if selected language is unchanged
        if(lang == CURRENT_SELECTED_LANGUAGE) return

        setLocale(this,lang)
        val intent = Intent(this, GeHomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.putExtra("lng",lang)
        overridePendingTransition(0, 0);
        intent.putExtra("SelectedClass",SELECTED_CLASS)
        finish()
        overridePendingTransition(0, 0);
        startActivity(intent)
        //overridePendingTransition(0, 0)
    }

    private fun slideInRightOutLeft() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}