package com.appninjas.recruiterheaven.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.model.Vacancy
import com.appninjas.recruiterheaven.databinding.VacancyCardBinding

class VacancyAdapter(private val vacancyList: List<Vacancy>) : RecyclerView.Adapter<VacancyAdapter.Holder>() {

    inner class Holder(view: View, private val binding: VacancyCardBinding) : RecyclerView.ViewHolder(view) {
        fun bind(model: Vacancy) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = VacancyCardBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(vacancyList[position])
    }

    override fun getItemCount(): Int = vacancyList.size

}