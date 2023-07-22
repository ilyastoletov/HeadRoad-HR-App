package com.appninjas.recruiterheaven.presentation.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appninjas.domain.model.UserCredentials
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthenticationBinding? = null
    private val binding: FragmentAuthenticationBinding
        get() = _binding ?: throw Exception("Fragment authentication binding = null")

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.buttonLogin.setOnClickListener {
            if (validateFields()) {
                viewModel.loginUser(UserCredentials(
                    login = binding.enterLoginEditText.text.toString(),
                    password = binding.enterPasswordEditText.text.toString()
                ))
                viewModel.loggedUser.observe(viewLifecycleOwner) {user ->
                    if (user == null) {
                        Toast.makeText(
                            requireContext(),
                            "Неправильный логин или пароль",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        findNavController().navigate(R.id.navVacancies)
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean = if (binding.enterLoginEditText.text.toString().isEmpty() || binding.enterPasswordEditText.text.toString().isEmpty()) {
        Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
        false
    } else {
        true
    }

}