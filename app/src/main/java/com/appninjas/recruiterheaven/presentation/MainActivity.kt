package com.appninjas.recruiterheaven.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationSetup()
    }

    private fun bottomNavigationSetup() {
        val bottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment)

        bottomNavigationView.setupWithNavController(navController)
    }

}