package com.muramsyah.mygithubusers.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muramsyah.mygithubusers.R
import com.muramsyah.mygithubusers.adapter.FavoriteUsersAdapter
import com.muramsyah.mygithubusers.database.UserHelper
import com.muramsyah.mygithubusers.databinding.ActivityFavoriteBinding
import com.muramsyah.mygithubusers.model.GithubUsers
import com.muramsyah.mygithubusers.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteUsersAdapter: FavoriteUsersAdapter
    private lateinit var favoriteModel: FavoriteModel

    private lateinit var userHelper: UserHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(getString(R.string.title_favorite_users))

        userHelper = UserHelper.getInstance(this)

        favoriteUsersAdapter = FavoriteUsersAdapter()
        favoriteUsersAdapter.notifyDataSetChanged()

        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = favoriteUsersAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteModel = FavoriteModel(application)
        userHelper.open()
        favoriteModel.getGithubUsers().observe(this, {
            if (it != null) {
                favoriteUsersAdapter.setData(it)
                binding.rvFavorite.adapter = favoriteUsersAdapter

                favoriteUsersAdapter.setOnItemClickCallback(object :
                    FavoriteUsersAdapter.OnItemClickCallBack {
                    override fun onItemClicked(data: GithubUsers) {
                        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                                putExtra(DetailActivity.EXTRA_DATA, data)
                            }
                        startActivity(intent)
                    }
                })
            }
        })
        userHelper.close()
    }

}