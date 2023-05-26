package com.appninjas.recruiterheaven.presentation.screens.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.presentation.utils.LOGIN_KEY
import com.appninjas.recruiterheaven.presentation.utils.SHARED_PREFS_NAME
import com.appninjas.recruiterheaven.presentation.utils.USER_STATUS

class SplashFragment : Fragment() {

    private val sharedPrefs: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userLoggedIn = sharedPrefs.getBoolean(LOGIN_KEY, false)
        if (userLoggedIn) {
            val userStatus = sharedPrefs.getString(USER_STATUS, "")
            if (userStatus == "RECRUITER") {
                findNavController().navigate(R.id.navVacancies)
            } else {
                Log.d("SUKAAAA", "Admin actions")
            }
        } else {
            findNavController().navigate(R.id.authFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }


}