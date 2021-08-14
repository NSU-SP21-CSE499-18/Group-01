package com.ece.nsu.spring2021.cse499.arschoolbook.adpters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ece.nsu.spring2021.cse499.arschoolbook.R


class SelectFigureAdapter(private val mList: Array<String>, private val context: Context) : RecyclerView.Adapter<SelectFigureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = mList[position]

        holder.textView.setOnClickListener {

            //Start intent here.......

            Toast.makeText(context, holder.textView.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {return mList.size }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
