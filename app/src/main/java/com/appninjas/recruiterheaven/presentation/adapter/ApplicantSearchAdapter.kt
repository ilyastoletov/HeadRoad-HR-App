package com.appninjas.recruiterheaven.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.model.Applicant
import com.appninjas.recruiterheaven.databinding.ApplicantsSearchItemBinding
import com.appninjas.recruiterheaven.presentation.adapter.model.SocialNetwork
import com.squareup.picasso.Picasso

class ApplicantSearchAdapter(private val profileImageCallback: ApplicantProfileImageCallback,
                             private val searchButtonCallback: ApplicantSearchButtonCallback,
                             private val socialNetworkClickCallback: SocialNetworkAdapter.SocialNetworkClickCallback) : RecyclerView.Adapter<ApplicantSearchAdapter.ApplicantSearchHolder>() {

    var applicantsList: List<Applicant> = listOf()

    inner class ApplicantSearchHolder(itemView: View, private val binding: ApplicantsSearchItemBinding) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Applicant) {
            with(binding) {
                applicantSearchCardName.text = model.name
                applicantAgeAndCitySearchCard.text = "${model.age} лет, ${model.city}"
                applicantJobSearchCard.text = model.profession
                applicantJobInfoSearch.text = "${model.wanted_salary} • Стаж ${model.job_experience} года/лет"

                Picasso.get().load(model.photo_url).into(applicantSearchProfileImage)

                applicantSearchProfileImage.setOnClickListener { profileImageCallback.onClick(model) }
                applicantSearchSetStatusButton.setOnClickListener { searchButtonCallback.onClick(model) }

                val socialNetworkAdapter = SocialNetworkAdapter(model.social_media_links.map { SocialNetwork(link = it) }, socialNetworkClickCallback)
                applicantSearchCardSocialsRv.apply {
                    adapter = socialNetworkAdapter
                    layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantSearchHolder {
        val binding = ApplicantsSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplicantSearchHolder(binding.root, binding)
    }

    fun setList(newList: List<Applicant>) {
        applicantsList = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ApplicantSearchHolder, position: Int) {
        holder.bind(applicantsList[position])
    }

    override fun getItemCount(): Int = applicantsList.size

    interface ApplicantProfileImageCallback {
        fun onClick(model: Applicant)
    }

    interface ApplicantSearchButtonCallback {
        fun onClick(model: Applicant)
    }

}