package com.ece.nsu.spring2021.cse499.arschoolbook.utils

import android.content.Context
import android.util.Log
import com.ece.nsu.spring2021.cse499.arschoolbook.R
import com.ece.nsu.spring2021.cse499.arschoolbook.activities.AugmentedImagesActivity

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

    fun getDbPathForChapterAndClass(className: String, chapterNo: String, context: Context)
    : String {

        var dbPath = ""

        if(className=="Class 7" || className=="সপ্তম শ্রেণি") {
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

        // todo: handle other classes

        return dbPath
    }

    @JvmStatic
    fun getModelUrlBasedOnFigureName(figureName: String, context: Context): String? {

        when(figureName){

            context.getString(R.string.fig_no_1_1) -> return ArUtil.VIRUS_MODEL_URL

            context.getString(R.string.fig_no_2_1a) -> return ArUtil.PLANT_CELL_MODEL_URL

            context.getString(R.string.fig_no_2_1b) -> return ArUtil.ANIMAL_CELL_MODEL_URL

            context.getString(R.string.fig_no_2_6) -> return ArUtil.NEURON_MODEL_URL

            context.getString(R.string.fig_no_4_3) -> return ArUtil.LUNG_MODEL_URL

            context.getString(R.string.fig_no_5_1) -> return ArUtil.DIGESTIVE_MODEL_URL

            context.getString(R.string.fig_no_12_1) -> return ArUtil.SOLAR_SYSTEM_MODEL_URL
        }

        return null
    }

    fun getFigureNameListFromSelectedClassAndChapter(className: String, chapterNo: String,
                                                     context: Context): Array<String> {

        if(className=="Class 7" || className=="সপ্তম শ্রেণি") {
            when (chapterNo) {
                "Chapter 1","অধ্যায় ১" -> {
                    return context.resources.getStringArray(R.array.fig_ch_1)
                }
                "Chapter 2","অধ্যায় ২" -> {
                    return context.resources.getStringArray(R.array.fig_ch_2)
                }
                "Chapter 3","অধ্যায় ৩" -> {
                    return context.resources.getStringArray(R.array.fig_ch_3)
                }
                "Chapter 4","অধ্যায় ৪" -> {
                    return context.resources.getStringArray(R.array.fig_ch_4)
                }
                "Chapter 5", "অধ্যায় ৫" -> {
                    return context.resources.getStringArray(R.array.fig_ch_5)
                }
                "Chapter 6", "অধ্যায় ৬" -> {
                    return context.resources.getStringArray(R.array.fig_ch_6)
                }
                "Chapter 7","অধ্যায় ৭" -> {
                    return context.resources.getStringArray(R.array.fig_ch_7)
                }
                "Chapter 8","অধ্যায় ৮" -> {
                    return context.resources.getStringArray(R.array.fig_ch_8)
                }
                "Chapter 9","অধ্যায় ৯" -> {
                    return context.resources.getStringArray(R.array.fig_ch_9)
                }
                "Chapter 10","অধ্যায় ১০" -> {
                    return context.resources.getStringArray(R.array.fig_ch_10)
                }
                "Chapter 11","অধ্যায় ১১" -> {
                    return context.resources.getStringArray(R.array.fig_ch_11)
                }
                "Chapter 12", "অধ্যায় ১২" -> {
                    return context.resources.getStringArray(R.array.fig_ch_12)
                }
                "Chapter 13", "অধ্যায় ১৩" -> {
                    return context.resources.getStringArray(R.array.fig_ch_13)
                }
                "Chapter 14", "অধ্যায় ১৪" -> {
                    return context.resources.getStringArray(R.array.fig_ch_14)
                }
                else -> return emptyArray()
            }
        }

        // todo: handle other classes

        return emptyArray()
    }

    /**
     * get model asset file uri from assets based on detected image index
     * @param detectedImageIndex index of detected image on the Ar image database
     * @return path of the model saved in assets folder
     */
    @JvmStatic
    fun getModelAssetsFileForDetectedImageIndex(detectedImageIndex: Int): String? {
        var modelUri: String? = null
        when (detectedImageIndex) {
            ArUtil.SOLAR_SYSTEM_IMG_ID -> modelUri = ArUtil.SOLAR_SYSTEM_MODEL_FILE

            ArUtil.VIRUS_IMG_ID -> modelUri = ArUtil.VIRUS_MODEL_FILE

            ArUtil.AMOEBA_IMG_ID -> modelUri = null

            ArUtil.ENTAMOEBA_IMG_ID -> modelUri = null

            ArUtil.PLANT_CELL_IMG_ID -> modelUri = ArUtil.PLANT_CELL_MODEL_FILE

            ArUtil.ANIMAL_CELL_IMG_ID -> modelUri = ArUtil.ANIMAL_CELL_MODEL_FILE

            ArUtil.NUCLEUS_IMG_ID -> modelUri = null

            ArUtil.NEURON_IMG_ID -> modelUri = ArUtil.NEURON_MODEL_FILE

            ArUtil.LUNG_IMG_ID -> modelUri = ArUtil.LUNG_MODEL_FILE

            ArUtil.DIGESTIVE_SYSTEM_IMG_ID -> modelUri = ArUtil.DIGESTIVE_MODEL_FILE
        }
        return modelUri
    }

    @JvmStatic
    fun getModelUriFromFigureName(figureName: String, context: Context): String? {
        var modelUriPath: String? = null

        when(figureName){

            context.getString(R.string.fig_no_1_1) -> modelUriPath = ArUtil.VIRUS_MODEL_FILE

            context.getString(R.string.fig_no_1_6) -> modelUriPath = null

            context.getString(R.string.fig_no_1_7) -> modelUriPath = null

            context.getString(R.string.fig_no_2_1a) -> modelUriPath = ArUtil.PLANT_CELL_MODEL_FILE

            context.getString(R.string.fig_no_2_1b) -> modelUriPath = ArUtil.ANIMAL_CELL_MODEL_FILE

            context.getString(R.string.fig_no_2_3) -> modelUriPath = null

            context.getString(R.string.fig_no_2_6) -> modelUriPath = ArUtil.NEURON_MODEL_FILE

            context.getString(R.string.fig_no_4_3) -> modelUriPath = ArUtil.LUNG_MODEL_FILE

            context.getString(R.string.fig_no_5_1) -> modelUriPath = ArUtil.DIGESTIVE_MODEL_FILE

            context.getString(R.string.fig_no_12_1) -> modelUriPath = ArUtil.SOLAR_SYSTEM_MODEL_FILE
        }

        return modelUriPath
    }
}