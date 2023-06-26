package com.appninjas.recruiterheaven.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.enums.VacancyStatus
import com.appninjas.domain.model.Vacancy
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.VacancyCardBinding

class VacancyAdapter(private val vacancyList: List<Vacancy>) : RecyclerView.Adapter<VacancyAdapter.Holder>() {

    inner class Holder(view: View, private val binding: VacancyCardBinding) : RecyclerView.ViewHolder(view) {
        fun bind(model: Vacancy) {
            with(binding) {
                vacancyStatus.apply {
                    when (model.vacancyStatus) {
                        VacancyStatus.OPEN -> {text = "Открыта"; setTextColor(ContextCompat.getColor(context, R.color.open_vacancy))}
                        VacancyStatus.PAUSED -> {text = "На паузе"; setTextColor(ContextCompat.getColor(context, R.color.paused_vacancy))}
                        VacancyStatus.STOPPED -> {text = "Закрыта"; setTextColor(ContextCompat.getColor(context, R.color.stopped_vacancy))}
                    }
                }
                vacancyQuantity.text = model.responderIds.size.toString() + " откликов"
                vacancyDate.text = model.createdAt
                vacancyName.text = model.title
            }
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