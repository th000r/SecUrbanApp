package de.tudarmstadt.smartcitystudyapp.matomo

import android.app.Activity
import de.tudarmstadt.smartcitystudyapp.SmartCityStudyApplication
import org.matomo.sdk.Tracker
import org.matomo.sdk.extra.TrackHelper

enum class MatomoCategory(val value: String) {
    MAIN_ACTIVITY("MainActivity"),
    PROFILE("ProfileFragment"),
    INCIDENTS("IncidentsFragment"),
    ACTIVITIES("ActivitiesFragment"),
    TEAM_ACITIVITES("TeamActivitiesFragment"),
    HELP("HelpFragment"),
    SITE_NOTICE("SiteNoticeFragment"),
    REPORTS("ReportsFragment"),
    WELCOME("WelcomeActivity"),
}

enum class MatomoAction(val value: String) {
    TOGGLE_MENU_TRUE("Menu Toggle True"),
    TOGGLE_MENU_FALSE("Menu Toggle True"),
    BUTTON_PRESS("Button Press"),
    BUTTON_TOGGLE_TRUE("Button Toggle True"),
    BUTTON_TOGGLE_FALSE("Button Toggle False"),
    CREATE_VIEW("Create View"),
    NAVIGATION("Navigation"),
}

object MatomoTracker {
    private lateinit var tracker: Tracker
    private lateinit var category: MatomoCategory
    private lateinit var path: String

    fun initTracker(t: Tracker) {
        tracker = t
    }

    fun setParams(c: MatomoCategory, p: String) {
        category = c
        path = p
    }

    fun track(category: String, action: String, name: String? = null, path: String? = null, value: Float? = null) {
        val eventBuilder: TrackHelper.EventBuilder = TrackHelper.track().event(category, action)

        if (name != null) {
            eventBuilder.name(name)
        }
        if (path != null) {
            eventBuilder.path(path)
        }
        if (value != null) {
            eventBuilder.value(value)
        }

        eventBuilder.with(tracker)
    }

    fun navigationMenuToggled(toggle: Boolean) {
        if (toggle) {
            TrackHelper.track().event(category.value, MatomoAction.TOGGLE_MENU_TRUE.value).path(path).with(tracker)
        } else {
            TrackHelper.track().event(category.value, MatomoAction.TOGGLE_MENU_FALSE.value).path(path).with(tracker)
        }
    }

    fun pressNavigationItem(navName: String) {
        TrackHelper.track().event(category.value, MatomoAction.NAVIGATION.value).name(navName).path(path).with(tracker)
    }

    fun initFragment() {
        TrackHelper.track().event(category.value, MatomoAction.CREATE_VIEW.value).path(path).with(tracker)
    }
}