package de.tudarmstadt.smartcitystudyapp.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPref {
    companion object {
        private val SHARED_PREF = "de.tudarmstadt.smartcitystudyapp"
        private val NOTIFICATION_KEY_STATUS = "notification_report_status"

        fun putNotificationStatus(con: Context, number: Int) {
            val pref = con.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
            val ed = pref.edit()
            ed.putInt(NOTIFICATION_KEY_STATUS, number)
            ed.apply()
        }

        fun getNotificationStatus(con: Context): Int {
            val pref = con.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
            return pref.getInt(NOTIFICATION_KEY_STATUS, 0)
        }

        fun registerSharedPrefChangeListener(con: Context, listener: SharedPreferences.OnSharedPreferenceChangeListener) {
            val pref = con.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
            pref.registerOnSharedPreferenceChangeListener(listener)
        }

        fun unregisterSharedPrefChangeListener(con: Context, listener: SharedPreferences.OnSharedPreferenceChangeListener) {
            val pref = con.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
            pref.registerOnSharedPreferenceChangeListener(listener)
        }
    }
}