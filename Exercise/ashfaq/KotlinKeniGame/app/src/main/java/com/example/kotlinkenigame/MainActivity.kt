package com.example.kotlinkenigame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var score : Int = 0
    var imageArray = ArrayList<ImageView>()
    var handler: Handler = Handler()
    var runable : Runnable = Runnable{ }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        score = 0

        imageArray = arrayListOf(imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9)
        hideImages()

        object : CountDownTimer(10000,1000){

            override fun onTick(millisUntilFinished: Long) {
                timerLabel.text = "Time: "+ millisUntilFinished/1000
            }

            override fun onFinish() {
                timerLabel.text = "Time's over"
                handler.removeCallbacks(runable)
                for(image in imageArray){
                    image.visibility = View.INVISIBLE
                }
            }


        }.start()
    }

    fun hideImages(){
        runable = object : Runnable{
            override fun run() {

                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                val random = Random()
                val index = random.nextInt(8-0)
                imageArray[index].visibility = View.VISIBLE
                handler.postDelayed(runable, 400)
            }


        }
        handler.post(runable)
    }

    fun increaseScore(view: View) {
        score++
        scoreBoard.text = "Score: " + score

    }
}