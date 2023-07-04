package com.appninjas.recruiterheaven.presentation.core

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.ActivityMainBinding
import com.appninjas.recruiterheaven.presentation.utils.LOGIN_KEY
import com.appninjas.recruiterheaven.presentation.utils.SHARED_PREFS_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        checkIsUserAuthorized()
        bottomNavigationSetup()
    }

    private fun bottomNavigationSetup() {
        val bottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConf = AppBarConfiguration(setOf(R.id.navVacancies, R.id.navApplicants, R.id.navMetrics))

        setupActionBarWithNavController(navController, appBarConf)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            when(nd.id) {
                R.id.authFragment -> bottomNavigationView.visibility = View.GONE
                R.id.createVacancyFragment -> bottomNavigationView.visibility = View.GONE
                R.id.vacancyInfoFragment -> bottomNavigationView.visibility = View.GONE
                else -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    private fun checkIsUserAuthorized() {
        val sharedPrefs = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        val userLoggedIn = sharedPrefs.getBoolean(LOGIN_KEY, false)
        if (!userLoggedIn) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.authFragment)
        } else {
            findNavController(R.id.nav_host_fragment).navigate(R.id.navVacancies)
        }
    }

}