package com.muramsyah.mygithubusers.adapter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.muramsyah.mygithubusers.ui.detail.DetailActivity
import com.muramsyah.mygithubusers.ui.detail.FollowersFragment
import com.muramsyah.mygithubusers.ui.detail.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private lateinit var username: Bundle

    fun setUsername(username: Bundle) {
        this.username = username
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = username
        Log.d("username", "username in sectionPagerAdapter: ${username.getString(DetailActivity.EXTRA_USERNAME)}")
        return fragment as Fragment
    }
}