package com.muramsyah.mygithubusers.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.ybq.android.spinkit.style.Wave
import com.google.android.material.tabs.TabLayoutMediator
import com.muramsyah.mygithubusers.R
import com.muramsyah.mygithubusers.adapter.FavoriteUsersAdapter
import com.muramsyah.mygithubusers.adapter.SectionPagerAdapter
import com.muramsyah.mygithubusers.databinding.ActivityDetailBinding
import com.muramsyah.mygithubusers.model.GithubUsers
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailModel: DetailModel

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_USERNAME = "extra_username"

        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_followers,
            R.string.tab_text_following
        )
    }

    var isFavorite = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepare()
        showProgressBar(true)
    }

    private fun prepare() {
        val githubUsers = intent.getParcelableExtra<GithubUsers>(EXTRA_DATA) as GithubUsers
        setTitle(githubUsers.login)

        val bundle = Bundle().apply {
            putString(EXTRA_USERNAME, githubUsers.login)
        }

        checkFavoriteOfState(githubUsers.login)

        detailModel = DetailModel(application)
        detailModel = ViewModelProvider(this).get(DetailModel::class.java)
        detailModel.setGithubUserRetrofit(githubUsers.login)
        detailModel.getGithubUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    tvUsername.text = "@${it.login}"
                    tvName.text = it.name
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                    tvBio.text = it.bio
                    tvRepository.text = it.public_repos.toString()
                    tvLocation.text = it.location
                    tvCompany.text = it.company

                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .apply(RequestOptions().override(350, 350))
                        .into(imgItemPhoto)
                }
                showProgressBar(false)
            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.setUsername(bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        binding.fabFavorite.setOnClickListener {
            if (!isFavorite) {
                isFavorite = true
                val user = GithubUsers().apply {
                    login = githubUsers.login
                    avatar_url = githubUsers.avatar_url
                    url = githubUsers.url
                }
                detailModel.setFavoriteUser(user)
                Toast.makeText(this, "Kamu menambah ${githubUsers.login} kedalam favorite", Toast.LENGTH_SHORT).show()
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_white_24)
            } else {
                isFavorite = false
                detailModel.deleteByUsername(githubUsers.login)
                Toast.makeText(this, "Kamu menghapus ${githubUsers.login} dari favorite", Toast.LENGTH_SHORT).show()
                binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_white)
            }
        }
    }

    private fun checkFavoriteOfState(username: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val status = detailModel.checkIsFavoriteUser(username)
            if (status != 0) {
                isFavorite = true
                withContext(Dispatchers.Main) {
                    binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_white_24)
                }
            } else {
                isFavorite = false
                withContext(Dispatchers.Main) {
                    binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_white)
                }
            }
        }
    }

    private fun showProgressBar(state: Boolean) {
        val wave = Wave()
        binding.progressBar.setIndeterminateDrawable(wave)
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}