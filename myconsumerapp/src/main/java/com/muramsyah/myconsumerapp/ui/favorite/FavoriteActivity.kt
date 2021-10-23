package com.muramsyah.mygithubusers.ui.favorite

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ybq.android.spinkit.style.Wave
import com.muramsyah.myconsumerapp.R
import com.muramsyah.myconsumerapp.databinding.ActivityFavoriteBinding
import com.muramsyah.mygithubusers.adapter.FavoriteUsersAdapter
import com.muramsyah.mygithubusers.database.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.muramsyah.mygithubusers.model.GithubUsers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteUsersAdapter: FavoriteUsersAdapter
    private lateinit var favoriteModel: FavoriteModel

    companion object {
        const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle(resources.getString(R.string.title_favorite_users))

        favoriteUsersAdapter = FavoriteUsersAdapter()
        favoriteModel = FavoriteModel(application)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = favoriteUsersAdapter
        favoriteUsersAdapter.notifyDataSetChanged()

        favoriteUsersAdapter.setOnItemClickCallback(object : FavoriteUsersAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: GithubUsers) {
                Toast.makeText(this@FavoriteActivity, resources.getString(R.string.toast_item_selected, data.login), Toast.LENGTH_SHORT).show()
            }

        })

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadUsersAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadUsersAsync()
        } else {
            savedInstanceState.getParcelableArrayList<GithubUsers>(EXTRA_STATE) ?.also { favoriteUsersAdapter.mData = it }
        }

    }

    private fun loadUsersAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                favoriteModel.mappingHelper(cursor)
            }
            val users = deferredUsers.await()
            if (users.size > 0) {
                binding.tvDescription.visibility = View.GONE
                favoriteUsersAdapter.mData = users
            } else {
                binding.tvDescription.visibility = View.VISIBLE
                favoriteUsersAdapter.mData.clear()
                favoriteUsersAdapter.mData = ArrayList()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteUsersAdapter.mData)
    }

}