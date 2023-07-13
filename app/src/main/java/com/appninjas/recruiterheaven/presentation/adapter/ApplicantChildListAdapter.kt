package com.appninjas.recruiterheaven.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.model.Applicant
import com.appninjas.recruiterheaven.databinding.ApplicantProfileItemBinding
import com.squareup.picasso.Picasso

class ApplicantChildListAdapter(private val applicantsList: List<Applicant>,
                                private val applicantProfileCallback: ApplicantProfileCallback) : RecyclerView.Adapter<ApplicantChildListAdapter.ApplicantChildHolder>() {

    inner class ApplicantChildHolder(view: View, private val binding: ApplicantProfileItemBinding) : RecyclerView.ViewHolder(view) {
        fun bind(model: Applicant) {
            with(binding) {
                Picasso.get().load(model.photo_url).into(applicantPhoto)
                applicantName.text = model.name
                applicantShortDescription.text = "${model.age}, ${model.city}, ${model.education}"
                applicantProfileItem.setOnClickListener { applicantProfileCallback.onClick(model) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantChildHolder {
        val binding = ApplicantProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplicantChildHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ApplicantChildHolder, position: Int) {
        holder.bind(applicantsList[position])
    }

    override fun getItemCount(): Int = applicantsList.size

    interface ApplicantProfileCallback {
        fun onClick(model: Applicant)
    }

}