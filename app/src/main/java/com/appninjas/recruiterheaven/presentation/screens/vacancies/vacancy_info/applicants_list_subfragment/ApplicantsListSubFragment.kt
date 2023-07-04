package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info.applicants_list_subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appninjas.recruiterheaven.databinding.FragmentSubApplicantsListBinding

class ApplicantsListSubFragment : Fragment() {

    private lateinit var binding: FragmentSubApplicantsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSubApplicantsListBinding.inflate(inflater, container, false)
        return binding.root
    }

}