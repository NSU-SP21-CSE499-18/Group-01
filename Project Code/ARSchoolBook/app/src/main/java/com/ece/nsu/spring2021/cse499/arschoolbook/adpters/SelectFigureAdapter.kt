package com.ece.nsu.spring2021.cse499.arschoolbook.adpters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.activities.ViewSelectedArModelActivity


class SelectFigureAdapter(private val mList: Array<String>,
                          private val selectFigureAdapterCallback: SelectFigureAdapterCallback)
    : RecyclerView.Adapter<SelectFigureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.figure_item_view, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val figureName: String = mList[position]

        holder.textView.text = figureName

        holder.card.setOnClickListener {
            selectFigureAdapterCallback.onFigureItemClick(figureName)
        }
    }

    override fun getItemCount(): Int {return mList.size }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.fig_item_text_view)
        val card: CardView = itemView.findViewById(R.id.card)
    }

    interface SelectFigureAdapterCallback{
        fun onFigureItemClick(figureName: String)
    }
}
