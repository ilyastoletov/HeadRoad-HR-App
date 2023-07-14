package com.appninjas.recruiterheaven.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.SocialNetworksItemBinding
import com.appninjas.recruiterheaven.presentation.adapter.model.SocialNetwork

class SocialNetworkAdapter(private val socialNetworkLinks: List<SocialNetwork>,
                           private val socialNetworkCallback: SocialNetworkClickCallback) : RecyclerView.Adapter<SocialNetworkAdapter.SocialNetworksHolder>() {

    inner class SocialNetworksHolder(itemView: View, private val binding: SocialNetworksItemBinding) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: SocialNetwork) {
            binding.socialNetworkItem.setImageResource(if (model.link.contains("https://github.com")) {
                R.drawable.github_social
            } else if (model.link.contains("https://vk.com")) {
                R.drawable.vk_social
            } else if (model.link.contains("https://behance.com/")) {
                R.drawable.behance_social
            } else if (model.link.contains("https://linkedin.com/")) {
                R.drawable.linkedin_social
            } else if (model.link.contains("https://t.me/")) {
                R.drawable.telegram_social
            } else R.drawable.vk_social)
            binding.socialNetworkItem.setOnClickListener { socialNetworkCallback.onClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialNetworksHolder {
        val binding = SocialNetworksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SocialNetworksHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: SocialNetworksHolder, position: Int) {
        holder.bind(socialNetworkLinks[position])
    }

    override fun getItemCount(): Int = socialNetworkLinks.size

    interface SocialNetworkClickCallback {
        fun onClick(model: SocialNetwork)
    }
}