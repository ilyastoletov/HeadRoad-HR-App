package com.appninjas.recruiterheaven.presentation.screens.metrics.view_metrics

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuInflater
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.ActivityViewMetricsBinding
import com.github.mikephil.charting.components.AxisBase
import dagger.hilt.android.AndroidEntryPoint
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.formatter.ValueFormatter


@AndroidEntryPoint
class ViewMetricsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewMetricsBinding
    private var metricsId: Int = 0
    private val viewModel: ViewMetricsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewMetricsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        metricsId = intent.extras!!.getInt("metricsId", 0)
        initUI()
    }

    private fun initUI() {
        viewModel.getMetricsById(metricsId)
        viewModel.metricsInfo.observe(this@ViewMetricsActivity) {metrics ->
            with(binding) {
                metricsNameText.text = metrics.label
                sortingTypeText.text = "Сортировать по: " + metrics.sortingType
                sortingVacancyText.text = "По вакансии: " + metrics.vacancyTitle
                datesRangeText.text = "Диапазон дат: " + metrics.dateRange
            }
            buildMetrics(metrics.metricEntries, metrics.sortingType)
        }

        binding.metricsShowMenuButton.setOnClickListener { v ->
           val popup = PopupMenu(this@ViewMetricsActivity, v)
           val menuInflater = popup.menuInflater
            menuInflater.inflate(R.menu.delete_metrics_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.delete_metrics_menu_button -> {
                        confirmDeleteMetricsDialog()
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        binding.viewMetricsCloseButton.setOnClickListener { finish() }
    }

    private fun buildMetrics(metricsEntries: Map<String, Int>, metricsLabel: String) {
        val barEntriesList: ArrayList<BarEntry> = arrayListOf()
        val barChart = binding.metricsMainChart

        val metricsKeys: ArrayList<String> = metricsEntries.keys.toCollection(ArrayList())
        val formatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return metricsKeys[value.toInt()]
            }
        }

        for (i in 0 until metricsKeys.size) {
            barEntriesList.add(BarEntry(i.toFloat(), metricsEntries[metricsKeys[i]]!!.toFloat()))
        }

        val barXAxis = barChart.xAxis
        barXAxis.apply {
            granularity = 1f
            valueFormatter = formatter
        }

        val barDataSet = BarDataSet(barEntriesList, metricsLabel)
        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.invalidate()
    }

    private fun confirmDeleteMetricsDialog() {

        val dialogButtonsListener = DialogInterface.OnClickListener { dialog, element ->
            when(element) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteMetrics(metricsId)
                    Toast.makeText(this@ViewMetricsActivity, "Метрика удалена", Toast.LENGTH_SHORT).show()
                    finish()
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.cancel()
            }
        }

        val deleteMetricsDialog = AlertDialog.Builder(this)
            .setTitle("Подтверждение")
            .setMessage("Вы действительно хотите удалить эту метрику?")
            .setPositiveButton("Да", dialogButtonsListener)
            .setNegativeButton("Отмена", dialogButtonsListener)
            .setCancelable(true)
            .create()

        deleteMetricsDialog.show()

    }

}