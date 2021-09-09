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
import com.ece.nsu.spring2021.cse499.arschoolbook.activities.GeHomeActivity
import com.ece.nsu.spring2021.cse499.arschoolbook.activities.SelectFigureActivity

//Add callback function
class SelectClassAdapter(private val mList: Array<String>, private val context: Context) : RecyclerView.Adapter<SelectClassAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_item_view, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = mList[position]

        //Replace it with callbacks
        holder.layout.setOnClickListener {

            val intent = Intent(context, GeHomeActivity::class.java)
            intent.putExtra("SelectedClass", holder.textView.text.toString())
            context.startActivity(intent)
            //Toast.makeText(context, holder.textView.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int {return mList.size }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.className)
        val layout: LinearLayout = itemView.findViewById(R.id.class_layout)
    }


}
