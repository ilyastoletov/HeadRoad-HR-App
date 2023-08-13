package com.appninjas.recruiterheaven.presentation.screens.metrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appninjas.recruiterheaven.databinding.FragmentMetricsBinding

class MetricsFragment : Fragment() {

    private lateinit var binding: FragmentMetricsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMetricsBinding.inflate(inflater, container, false)
        return binding.root
    }

}