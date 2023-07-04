package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
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
        initMenu()
        initTabLayout()
        initUI()
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.vacancy_edit_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when(menuItem.itemId) {
                R.id.edit_vacancy_button -> { true }
                R.id.delete_vacancy_button -> { true }
                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
                    R.id.edit_vacancy_button -> { true }
                    R.id.delete_vacancy_button -> { true }
                    else -> false
                }
            }
            popup.show()
        }
    }

    private fun getVacancyId() = requireArguments().getString("vacancyId")!!

}