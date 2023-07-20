package com.appninjas.recruiterheaven.presentation.screens.applicant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appninjas.recruiterheaven.databinding.FragmentApplicantsBinding

class ApplicantsFragment : Fragment() {

    private lateinit var binding: FragmentApplicantsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentApplicantsBinding.inflate(inflater, container, false)
        return binding.root
    }
}