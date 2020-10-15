package com.yasser.recipes.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

private const val FILTER_BY = "Filter_by_Int"


class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

     fun SaveFilterType(value: Int) {

        preference.edit().putInt(
            FILTER_BY, value
        ).apply()
    }

     fun GetFilterType(): Int {
        return preference.getInt(FILTER_BY, -1)
    }

}