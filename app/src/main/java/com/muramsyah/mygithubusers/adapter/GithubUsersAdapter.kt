package com.muramsyah.mygithubusers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muramsyah.mygithubusers.databinding.ItemGithubuserListBinding
import com.muramsyah.mygithubusers.model.GithubUsers

class GithubUsersAdapter : RecyclerView.Adapter<GithubUsersAdapter.GithubUsersViewHolder>() {

    private val mData = ArrayList<GithubUsers>()

    private lateinit var onItemClickCallback: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: GithubUsers)
    }

    fun setData(items: ArrayList<GithubUsers>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUsersViewHolder {
        val view = ItemGithubuserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GithubUsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: GithubUsersViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class GithubUsersViewHolder(private val binding: ItemGithubuserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GithubUsers) {
            with (binding) {
                Glide.with(itemView.context)
                    .load(data.avatar_url)
                    .apply(RequestOptions().override(350, 350))
                    .into(imgItemPhoto)

                tvUsername.text = "${data.login}"
                tvUserUrl.text = data.url
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(data)
                }
            }
        }
    }
}