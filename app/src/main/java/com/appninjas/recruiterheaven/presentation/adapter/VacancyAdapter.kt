package com.appninjas.recruiterheaven.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.enums.VacancyStatus
import com.appninjas.domain.model.Vacancy
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.NoInternetNoticeLayoutBinding
import com.appninjas.recruiterheaven.databinding.VacancyCardBinding
import java.lang.IllegalArgumentException

class VacancyAdapter(private val hasInternet: Boolean,
                     private val vacancyCardListener: VacancyClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val LOADING_STATE = 0
        const val LOADED_SUCCESSFULLY_STATE = 1
    }

    private var vacancyList: List<Vacancy> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return if ((hasInternet && vacancyList.isEmpty()) || !hasInternet) {
            LOADING_STATE
        } else {
            LOADED_SUCCESSFULLY_STATE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            LOADING_STATE -> {
                val inflater = LayoutInflater.from(parent.context)
                val loadingLayoutView = inflater.inflate(R.layout.loading_layout, parent, false)
                LoadingHolder(loadingLayoutView)
            }
            LOADED_SUCCESSFULLY_STATE -> {
                val binding = VacancyCardBinding.inflate(LayoutInflater.from(parent.context))
                VacancyHolder(binding.root, binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is LoadingHolder -> {}
            is VacancyHolder -> { holder.bind(vacancyList[position]) }
        }
    }

    fun setList(newList: List<Vacancy>) {
        vacancyList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if ((hasInternet && vacancyList.isEmpty()) || !hasInternet) {
            1
        } else {
            vacancyList.size
        }
    }

    inner class VacancyHolder(view: View, private val binding: VacancyCardBinding) : RecyclerView.ViewHolder(view) {
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
                newApplicantsQuantity.text = model.newApplicantsCount.toString() + " новых"
                vacancyCard.setOnClickListener{ vacancyCardListener.onClick(model.vacancyId) }
            }
        }
    }

    inner class LoadingHolder(view: View) : RecyclerView.ViewHolder(view) {}

    interface VacancyClickListener {
        fun onClick(vacancyId: String)
    }

}