package de.tudarmstadt.smartcitystudyapp.featuremanager

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.tudarmstadt.smartcitystudyapp.utils.getJsonDataFromAsset

class FeatureManager {
    companion object {

        // read list of available features from json config
        fun readFeatures(applicationContext: Context): Features {
            val jsonFileString = getJsonDataFromAsset(applicationContext, "feature-config.json")
            val gson = Gson()
            val featuresType = object : TypeToken<Features>() {}.type
            return gson.fromJson(jsonFileString, featuresType)
        }
    }
}