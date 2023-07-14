package com.appninjas.recruiterheaven.presentation.screens.applicant.set_status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appninjas.recruiterheaven.databinding.FragmentApplicantSetStatusBinding

class ApplicantSetStatusFragment : Fragment() {

    private lateinit var binding: FragmentApplicantSetStatusBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentApplicantSetStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

}