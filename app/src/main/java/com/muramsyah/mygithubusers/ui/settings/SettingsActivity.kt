package com.muramsyah.mygithubusers.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muramsyah.mygithubusers.R
import com.muramsyah.mygithubusers.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var settingPreferenceFragment: SettingPreferenceFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(resources.getString(R.string.settings))

        settingPreferenceFragment = SettingPreferenceFragment()
        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingPreferenceFragment()).commit()
    }
}