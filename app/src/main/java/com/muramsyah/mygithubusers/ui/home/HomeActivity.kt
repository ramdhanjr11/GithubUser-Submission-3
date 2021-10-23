package com.muramsyah.mygithubusers.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ybq.android.spinkit.style.Wave
import com.muramsyah.mygithubusers.R
import com.muramsyah.mygithubusers.adapter.GithubUsersAdapter
import com.muramsyah.mygithubusers.databinding.ActivityHomeBinding
import com.muramsyah.mygithubusers.model.GithubUsers
import com.muramsyah.mygithubusers.ui.detail.DetailActivity
import com.muramsyah.mygithubusers.ui.favorite.FavoriteActivity
import com.muramsyah.mygithubusers.ui.profile.ProfileActivity
import com.muramsyah.mygithubusers.ui.settings.SettingsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var githubUsersAdapter: GithubUsersAdapter
    private lateinit var githubUserModel: HomeModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(resources.getString(R.string.title_home))

        prepare()
        showGithubUsersList()
    }

    private fun prepare() {
        showProgressBar(false)
        githubUsersAdapter = GithubUsersAdapter()
        githubUsersAdapter.notifyDataSetChanged()
        githubUserModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeModel::class.java)
    }

    private fun showGithubUsersList() {
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = githubUsersAdapter

        githubUsersAdapter.setOnItemClickCallback(object : GithubUsersAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: GithubUsers) {
                val githubUsers = GithubUsers().apply {
                    login = data.login
                    avatar_url = data.avatar_url
                    url = data.url
                }

                var intent = Intent(this@HomeActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_DATA, githubUsers)
                }
                startActivity(intent)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()) {
                    showProgressBar(false)
                    val nothing = ArrayList<GithubUsers>()
                    githubUsersAdapter.setData(nothing)
                } else {
                    showTvWelcome(false)
                    showProgressBar(true)
                    githubUserModel.setGithubUser(newText)
                }
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        actionSelected(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    fun actionSelected(item: Int) {
        when (item) {
            R.id.action_profile -> {
                val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.action_change_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.action_favorite -> {
                val intent = Intent(this@HomeActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.action_settings -> {
                val intent = Intent(this@HomeActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun showProgressBar(state: Boolean) {
        val wave = Wave()
        binding.progressBar.setIndeterminateDrawable(wave)
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    fun showTvWelcome(state: Boolean) {
        if (state) {
            binding.tvWelcome.visibility = View.VISIBLE
        } else {
            binding.tvWelcome.visibility = View.GONE
        }
    }

    override fun onResume() {
        githubUserModel.getGithubUsers().observe(this, {
            if (it != null) {
                showProgressBar(false)
                showTvWelcome(false)
                githubUsersAdapter.setData(it)
            }
        })
        super.onResume()
    }
}