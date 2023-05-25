package com.appninjas.recruiterheaven.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appninjas.recruiterheaven.databinding.FragmentApplicantsBinding

class ApplicantsFragment : Fragment() {

    private val viewModel: ApplicantsViewModel by lazy {
        ViewModelProvider(this)[ApplicantsViewModel::class.java]
    }

    private var _binding: FragmentApplicantsBinding? = null
    private val binding: FragmentApplicantsBinding
        get() = _binding ?: throw Exception("FragmentApplicantsBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApplicantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ApplicantsFragment()
    }
}