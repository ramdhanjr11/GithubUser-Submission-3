package com.muramsyah.mygithubusers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muramsyah.mygithubusers.databinding.ItemGithubuserListBinding
import com.muramsyah.mygithubusers.model.GithubUsers

class FavoriteUsersAdapter() : RecyclerView.Adapter<FavoriteUsersAdapter.FavoriteUsersViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUsersAdapter.FavoriteUsersViewHolder {
        val view = ItemGithubuserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteUsersAdapter.FavoriteUsersViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class FavoriteUsersViewHolder(private val binding: ItemGithubuserListBinding) : RecyclerView.ViewHolder(binding.root) {
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