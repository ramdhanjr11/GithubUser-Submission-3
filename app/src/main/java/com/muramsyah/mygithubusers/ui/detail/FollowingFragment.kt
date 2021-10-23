package com.muramsyah.mygithubusers.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ybq.android.spinkit.style.Wave
import com.muramsyah.mygithubusers.R
import com.muramsyah.mygithubusers.adapter.GithubUsersAdapter
import com.muramsyah.mygithubusers.databinding.FragmentFollowingBinding
import com.muramsyah.mygithubusers.model.GithubUsers

class FollowingFragment : Fragment(R.layout.fragment_following) {

    private var _binding : FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GithubUsersAdapter
    private lateinit var followingModel: FollowingModel
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowingBinding.bind(view)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapter = GithubUsersAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = adapter
        binding.rvFollowing.setHasFixedSize(true)

        showProgressBar(true)

        followingModel = FollowingModel()
        followingModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingModel::class.java)
        followingModel.setUserFollowing(username)
        followingModel.getUserFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                showProgressBar(false)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
                adapter.setOnItemClickCallback(object : GithubUsersAdapter.OnItemClickCallBack {
                    override fun onItemClicked(data: GithubUsers) {
                        Toast.makeText(activity, "${context?.resources?.getString(R.string.toast_item_selected, data.login)}", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        })
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}