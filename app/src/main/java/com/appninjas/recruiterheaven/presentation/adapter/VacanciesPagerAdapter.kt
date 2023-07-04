package com.appninjas.recruiterheaven.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info.applicants_list_subfragment.ApplicantsListSubFragment
import com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info.info_subfragment.InfoSubFragment

class VacanciesPagerAdapter(fragmentActivity: FragmentActivity, private val vacancyId: String) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> InfoSubFragment(vacancyId)
        1 -> ApplicantsListSubFragment()
        else -> InfoSubFragment(vacancyId)
    }

}