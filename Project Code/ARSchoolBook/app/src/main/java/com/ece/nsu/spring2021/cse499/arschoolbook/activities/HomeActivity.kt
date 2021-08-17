package com.ece.nsu.spring2021.cse499.arschoolbook.activities

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import belka.us.androidtoggleswitch.widgets.ToggleSwitch
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import java.util.*


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toggleSwitch : ToggleSwitch = findViewById(R.id.lng_switch)


        val bundle: Bundle? = intent.extras
        val language = bundle?.get("lng")

        if(language!=null)
        {
            if(language == "bn")
            {
                toggleSwitch.checkedTogglePosition = 1
            }
            else
            {
                toggleSwitch.checkedTogglePosition = 0
            }
        }


        toggleSwitch.setOnToggleSwitchChangeListener { position, isChecked ->
            if(position==1)
            {
                setLang("bn")
            }
            else {
                setLang("en")
            }
        }
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
        setLocale(this,lang)
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("lng",lang)
        finish()
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun slideInRightOutLeft() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun onClick(v:View)
    {
        val intent = Intent(this, ContentActivity::class.java)

        when(v.id){
            R.id.ch_1 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_1))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_1))
            }
            R.id.ch_2 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_2))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_2))
            }
            R.id.ch_3 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_3))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_3))
            }
            R.id.ch_4 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_4))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_4))
            }
            R.id.ch_5 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_5))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_5))
            }
            R.id.ch_6 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_6))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_6))
            }
            R.id.ch_7 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_7))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_7))
            }
            R.id.ch_8 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_8))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_8))
            }
            R.id.ch_9 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_9))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_9))
            }
            R.id.ch_10 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_10))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_10))
            }
            R.id.ch_11 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_11))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_11))
            }
            R.id.ch_12 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_12))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_12))
            }
            R.id.ch_13 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_13))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_13))
            }
            R.id.ch_14 ->
            {
                intent.putExtra("Chapter-Name",getString(R.string.ch_name_14))
                intent.putExtra("Chapter-No",getString(R.string.ch_no_14))
            }
        }

        startActivity(intent)
        slideInRightOutLeft()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}