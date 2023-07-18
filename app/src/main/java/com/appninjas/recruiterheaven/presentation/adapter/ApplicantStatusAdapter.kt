package com.appninjas.recruiterheaven.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.recruiterheaven.databinding.ApplicantParentLayoutBinding
import com.appninjas.recruiterheaven.presentation.adapter.model.ApplicantParentItem
import com.appninjas.recruiterheaven.presentation.utils.Utils.setInvisible
import com.appninjas.recruiterheaven.presentation.utils.Utils.setVisible

class ApplicantStatusAdapter(private val applicantProfileCallback: ApplicantChildListAdapter.ApplicantProfileCallback,
                             private val pageControlCallback: PageControlCallback) : RecyclerView.Adapter<ApplicantStatusAdapter.ApplicantParentItemHolder>() {

    var applicantParentList: ArrayList<ApplicantParentItem> = arrayListOf()

    private val TAG = "Applicant Status Adapter"

    inner class ApplicantParentItemHolder(view: View, private val binding: ApplicantParentLayoutBinding) : RecyclerView.ViewHolder(view) {
        fun bind(model: ApplicantParentItem) {
            binding.applicantStatusText.text = model.title
            binding.applicantCountText.text = model.applicantsList.size.toString()

            val applicantsListAdapter = ApplicantChildListAdapter(applicantProfileCallback)
            binding.applicantListRv.apply {
                setHasFixedSize(true)
                adapter = applicantsListAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
            }

            binding.applicantsListLayout.setOnClickListener {
                if (!model.isExpanded) {
                    model.isExpanded = true
                    pageControlCallback.loadFirstPage(model, applicantsListAdapter)
                    binding.applicantListRv.setVisible()
                    if (model.pagesCount > 1) {
                        binding.pageControlButtons.setVisible()
                    }
                    if (model.currentPage == 1) {
                        binding.previousPageButton.setInvisible()
                    }
                } else {
                    model.isExpanded = false
                    model.currentPage = 1
                    binding.applicantListRv.setInvisible()
                    binding.pageControlButtons.setInvisible()
                }
                binding.applicantsButtonSwitcher.showNext()
            }

            binding.pageControlButtons.setOnClickListener {
                Log.d(TAG, "Page control buttons click listener worked")
                if (model.currentPage >= model.pagesCount) {
                    binding.nextPageButton.setInvisible()
                } else {
                    binding.nextPageButton.setVisible()
                }
                if (model.currentPage == 1) {
                    binding.previousPageButton.setInvisible()
                }
            }

            binding.previousPageButton.setOnClickListener {
                pageControlCallback.onPreviousPagePressed(model, applicantsListAdapter)
                if (model.currentPage == 1) {
                    it.setInvisible()
                    binding.nextPageButton.setVisible()
                } else {
                    it.setVisible()
                }
            }

            binding.nextPageButton.setOnClickListener {
                pageControlCallback.onNextPagePressed(model, applicantsListAdapter)
                if (model.currentPage >= model.pagesCount) {
                    it.setInvisible()
                    binding.previousPageButton.setVisible()
                } else {
                    it.setVisible()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantParentItemHolder {
        val binding = ApplicantParentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    interface PageControlCallback {
        fun loadFirstPage(model: ApplicantParentItem, childAdapter: ApplicantChildListAdapter)
        fun onPreviousPagePressed(model: ApplicantParentItem, childAdapter: ApplicantChildListAdapter)
        fun onNextPagePressed(model: ApplicantParentItem, childAdapter: ApplicantChildListAdapter)
    }

}