package com.ece.nsu.spring2021.cse499.arschoolbook.utils

import android.content.Context
import com.ece.nsu.spring2021.cse499.arschoolbook.R

object ResourceFetcherUtil {

    fun getChapterNamesFromSelectedClass(className: String, context: Context): Array<String> {

        return when (className) {
            "Class 7" -> {
                context.resources.getStringArray(R.array.chapter_names_c7)
            }
            else -> emptyArray()
        }
    }

    fun getClassNameTitle(className: String, context: Context): String
    {
        if (className=="Class 6" || className=="ষষ্ঠ শ্রেণী")
        {
            return context.resources.getString(R.string.cl_6)
        }
        else if(className=="Class 7" || className=="সপ্তম শ্রেণি")
        {
            return context.resources.getString(R.string.cl_7)
        }
        else if(className=="Class 8" || className=="অষ্ঠম শ্রেণ")
        {
            return context.resources.getString(R.string.cl_8)
        }
        else if(className=="Class 9" || className=="নবম শ্রেণী")
        {
            return context.resources.getString(R.string.cl_9)
        }
        else
        {
            return context.resources.getString(R.string.cl_10)
        }
    }
}