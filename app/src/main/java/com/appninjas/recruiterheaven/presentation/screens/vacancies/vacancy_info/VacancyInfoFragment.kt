package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentVacancyInfoBinding
import com.appninjas.recruiterheaven.presentation.adapter.VacanciesPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VacancyInfoFragment : Fragment() {

    private lateinit var binding: FragmentVacancyInfoBinding
    private val viewModel: VacancyInfoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVacancyInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        initUI()
    }

    private fun initTabLayout() {
        binding.vacanciesInfoPager.adapter = VacanciesPagerAdapter(requireActivity(), getVacancyId())
        TabLayoutMediator(binding.vacanciesInfoTabLayout, binding.vacanciesInfoPager) { tab, pos ->
            when(pos) {
                0 -> tab.text = "Обзор"
                1 -> tab.text = "Кандидаты"
            }
        }.attach()
    }

    private fun initUI() {
        viewModel.getVacancyTitle(getVacancyId())
        viewModel.vacancyTitle.observe(viewLifecycleOwner) {title ->
            binding.vacancyTitleInfo.text = title
        }

        binding.backToVacanciesButton.setOnClickListener { findNavController().navigate(R.id.navVacancies) }

        binding.showPopupMenuButton.setOnClickListener { v ->
            val popup = PopupMenu(requireContext(), v)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.vacancy_edit_menu, popup.menu)
            popup.setOnMenuItemClickListener {menuItem ->
                when(menuItem.itemId) {
                    R.id.edit_vacancy_button -> {
                        val bundle = Bundle()
                        bundle.putString("vacancyId", getVacancyId())
                        findNavController().navigate(R.id.createVacancyFragment, bundle)
                        true
                    }
                    R.id.delete_vacancy_button -> { deleteVacancyConfirmation(); true }
                    else -> false
                }
            }
            popup.show()
        }
    }

    private fun getVacancyId() = requireArguments().getString("vacancyId")!!

    private fun deleteVacancyConfirmation() {
        val dialogButtonsClickListener = DialogInterface.OnClickListener { dialog, element ->
            when(element) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteVacancy(getVacancyId())
                    findNavController().navigate(R.id.navVacancies)
                    Toast.makeText(requireContext(), "Вакансия удалена", Toast.LENGTH_SHORT).show()
                }
                DialogInterface.BUTTON_NEGATIVE -> dialog.cancel()
            }
        }
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Удалить вакансию?")
            .setMessage("Вы действительно хотите удалить эту вакансию?")
            .setPositiveButton("Да", dialogButtonsClickListener)
            .setNegativeButton("Нет", dialogButtonsClickListener)
            .setCancelable(true)
            .create()
        dialog.show()
    }

}