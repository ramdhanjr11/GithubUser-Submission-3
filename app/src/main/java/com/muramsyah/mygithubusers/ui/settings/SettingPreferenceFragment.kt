package com.muramsyah.mygithubusers.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.muramsyah.mygithubusers.R
import com.muramsyah.mygithubusers.model.Reminder
import com.muramsyah.mygithubusers.service.AlarmReceiver

class SettingPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var swReminder: SwitchPreference
    private lateinit var key_reminder: String
    private lateinit var reminder: Reminder

    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        const val DEFAULT_VALUE = false
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
    }

    private fun init() {
        key_reminder = resources.getString(R.string.key_reminder)
        swReminder = findPreference<SwitchPreference>(key_reminder) as SwitchPreference
        reminder = Reminder()
        alarmReceiver = AlarmReceiver()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == key_reminder) {
            reminder.reminder = sharedPreferences?.getBoolean(key_reminder, DEFAULT_VALUE) as Boolean
            if (reminder.reminder) {
                context?.let { alarmReceiver.setRepeatingAlarm(it, AlarmReceiver.TYPE_REPEATING, "09:00", "Reminder") }
            } else {
                context?.let { alarmReceiver.cancelAlarm(it, AlarmReceiver.TYPE_REPEATING) }
            }
        }
    }
}