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

    fun getDbPathForChapterAndClass(chapterNo: String, selectedClass: String, context: Context)
    : String {

        var dbPath = ""

        if(selectedClass == context.resources.getString(R.string.cl_7)) {
            dbPath = NosqlDbPathUtils.CLASS_7_BOOK_NODE + "/"
            when(chapterNo){
                context.getString(R.string.ch_no_1) -> dbPath += NosqlDbPathUtils.CHAPTER_1_NODE + "/"
                context.getString(R.string.ch_no_2) -> dbPath += NosqlDbPathUtils.CHAPTER_2_NODE + "/"
                context.getString(R.string.ch_no_3) -> dbPath += NosqlDbPathUtils.CHAPTER_3_NODE + "/"
                context.getString(R.string.ch_no_4) -> dbPath += NosqlDbPathUtils.CHAPTER_4_NODE + "/"
                context.getString(R.string.ch_no_5) -> dbPath += NosqlDbPathUtils.CHAPTER_5_NODE + "/"
                context.getString(R.string.ch_no_6) -> dbPath += NosqlDbPathUtils.CHAPTER_6_NODE + "/"
                context.getString(R.string.ch_no_7) -> dbPath += NosqlDbPathUtils.CHAPTER_7_NODE + "/"
                context.getString(R.string.ch_no_8) -> dbPath += NosqlDbPathUtils.CHAPTER_8_NODE + "/"
                context.getString(R.string.ch_no_9) -> dbPath += NosqlDbPathUtils.CHAPTER_9_NODE + "/"
                context.getString(R.string.ch_no_10) -> dbPath += NosqlDbPathUtils.CHAPTER_10_NODE + "/"
                context.getString(R.string.ch_no_11) -> dbPath += NosqlDbPathUtils.CHAPTER_11_NODE + "/"
                context.getString(R.string.ch_no_12) -> dbPath += NosqlDbPathUtils.CHAPTER_12_NODE + "/"
                context.getString(R.string.ch_no_13) -> dbPath += NosqlDbPathUtils.CHAPTER_13_NODE + "/"
                context.getString(R.string.ch_no_14) -> dbPath += NosqlDbPathUtils.CHAPTER_14_NODE + "/"
            }
            dbPath += NosqlDbPathUtils.YOUTUBE_VIDEOS_NODE + "/"
        }

        return dbPath
    }
}