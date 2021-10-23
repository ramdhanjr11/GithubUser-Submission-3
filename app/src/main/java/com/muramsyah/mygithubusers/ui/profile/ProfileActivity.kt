package com.muramsyah.mygithubusers.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muramsyah.mygithubusers.R
import com.muramsyah.mygithubusers.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private var URL_DICODING = "https://www.dicoding.com/users/ramdhanjr690"
    private var URL_GITHUB = "https://github.com/ramdhanjr11"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(resources.getString(R.string.title_action_profile))

        binding.btnAccountDicoding.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(URL_DICODING)
            }
            startActivity(intent)
        }

        binding.btnAccountGithub.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(URL_GITHUB)
            }
            startActivity(intent)
        }
    }
}