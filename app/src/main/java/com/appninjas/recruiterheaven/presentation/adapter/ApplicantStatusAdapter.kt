package com.appninjas.recruiterheaven.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.recruiterheaven.databinding.ApplicantParentLayoutBinding
import com.appninjas.recruiterheaven.presentation.adapter.model.ApplicantParentItem

class ApplicantStatusAdapter() : RecyclerView.Adapter<ApplicantStatusAdapter.ApplicantParentItemHolder>() {

    private var applicantParentList: ArrayList<ApplicantParentItem> = arrayListOf()

    inner class ApplicantParentItemHolder(view: View, private val binding: ApplicantParentLayoutBinding) : RecyclerView.ViewHolder(view) {
        fun bind(model: ApplicantParentItem) {
            binding.applicantStatusText.text = model.title

            val applicantsListAdapter = ApplicantChildListAdapter(model.applicantsList)
            binding.applicantListRv.apply {
                setHasFixedSize(true)
                adapter = applicantsListAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
            }

            binding.applicantsListLayout.setOnClickListener {
                if (!model.isExpanded) {
                    model.isExpanded = true
                    binding.applicantListRv.visibility = View.VISIBLE
                } else {
                    model.isExpanded = false
                    binding.applicantListRv.visibility = View.GONE
                }
                binding.applicantsButtonSwitcher.showNext()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantParentItemHolder {
        val binding = ApplicantParentLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ApplicantParentItemHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ApplicantParentItemHolder, position: Int) {
        holder.bind(applicantParentList[position])
    }

    override fun getItemCount(): Int = applicantParentList.size

    fun setList(newList: ArrayList<ApplicantParentItem>) {
        applicantParentList = newList
        notifyDataSetChanged()
    }

}