package com.appninjas.recruiterheaven.presentation.screens.metrics

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.domain.model.Metrics
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentMetricsBinding
import com.appninjas.recruiterheaven.presentation.adapter.MetricsAdapter
import com.appninjas.recruiterheaven.presentation.screens.metrics.view_metrics.ViewMetricsActivity
import com.appninjas.recruiterheaven.presentation.utils.Utils.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MetricsFragment : Fragment() {

    private lateinit var binding: FragmentMetricsBinding
    private val viewModel: MetricsViewModel by viewModels()

    private lateinit var metricsAdapter: MetricsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMetricsBinding.inflate(inflater, container, false)
        metricsAdapter = MetricsAdapter(metricsClickListener)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {

        binding.metricsRecyclerView.apply {
            adapter = metricsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getMetrics()
        viewModel.metricsList.observe(viewLifecycleOwner) {metricsList ->
            if (metricsList.isEmpty()) {
                binding.noMetricsText.setVisible()
            } else {
                metricsAdapter.setList(metricsList)
            }
        }

        binding.addMetricsButton.setOnClickListener { findNavController().navigate(R.id.createMetricsFragment) }
    }

    private val metricsClickListener = object : MetricsAdapter.MetricsClickListener {
        override fun onClick(model: Metrics) {
            val metricsIntent = Intent(requireContext(), ViewMetricsActivity::class.java)
            metricsIntent.putExtra("metricsId", model.id)
            startActivity(metricsIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMetrics()
        viewModel.metricsList.observe(viewLifecycleOwner) {
            metricsAdapter.setList(it)
        }
    }

}