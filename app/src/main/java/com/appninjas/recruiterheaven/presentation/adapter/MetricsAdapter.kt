package com.appninjas.recruiterheaven.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.model.Metrics
import com.appninjas.recruiterheaven.databinding.MetricsItemBinding

class MetricsAdapter(private val metricsClickListener: MetricsClickListener) : RecyclerView.Adapter<MetricsAdapter.MetricsHolder>() {

    private var metricsList: List<Metrics> = listOf()

    inner class MetricsHolder(view: View, private val binding: MetricsItemBinding) : RecyclerView.ViewHolder(view) {
        fun bind(model: Metrics) {
            with(binding) {
                metricsNameItem.text = model.label
                metricsSortingType.text = "Сортировка по: " + model.sortingType
                metricsVacancyTitle.text = "По вакансии: " + model.vacancyTitle
                openMetricsButton.setOnClickListener { metricsClickListener.onClick(model) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetricsHolder {
        val binding = MetricsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MetricsHolder(binding.root, binding)
    }

    fun setList(newList: List<Metrics>) {
        metricsList = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MetricsHolder, position: Int) {
        holder.bind(metricsList[position])
    }

    override fun getItemCount(): Int = metricsList.size

    interface MetricsClickListener {
        fun onClick(model: Metrics)
    }

}