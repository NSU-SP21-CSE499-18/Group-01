package com.ece.nsu.spring2021.cse499.arschoolbook.utils.sharedPreferences;

import android.content.Context;

public class UserChoiceSharedPref extends SharedPrefsUtil{

    public static UserChoiceSharedPref build(Context context) {

        return new UserChoiceSharedPref(SharedPrefKeysUtil.USER_CHOICE_ID, context);
    }

    private UserChoiceSharedPref(String mSharedPreferenceId, Context mContext) {
        super(mSharedPreferenceId, mContext);
    }

    public void setSelectedClassName(String className) {
        saveStringData(SharedPrefKeysUtil.SELECTED_CLASS_KEY, className);
    }

    public String getSelectedClassName(String defaultClassName) {
        return getStringData(SharedPrefKeysUtil.SELECTED_CLASS_KEY, defaultClassName);
    }
}
