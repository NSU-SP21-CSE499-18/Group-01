package com.ece.nsu.spring2021.cse499.arschoolbook.adpters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.activities.ContentActivity
import com.ece.nsu.spring2021.cse499.arschoolbook.activities.GeContentActivity
import com.ece.nsu.spring2021.cse499.arschoolbook.activities.GeHomeActivity

class HomeAdapter(private val chapterNumbers: Array<String>,private val chapterNames: Array<String>,
                  private val context: Context) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chapter_item_view, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.chapterNo.text = chapterNumbers[position]
        holder.chapterName.text = chapterNames[position]

        holder.layout.setOnClickListener {

            //Start intent here.......
            val intent = Intent(context, GeContentActivity::class.java)
            intent.putExtra("SelectedChapter",position )
            context.startActivity(intent)

            Toast.makeText(context, holder.chapterName.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {return chapterNames.size }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val chapterNo: TextView = itemView.findViewById(R.id.chapterNumber_iv)
        val chapterName: TextView  = itemView.findViewById(R.id.chapterName)
        val layout: LinearLayout = itemView.findViewById(R.id.chapter_layout)

    }

}
