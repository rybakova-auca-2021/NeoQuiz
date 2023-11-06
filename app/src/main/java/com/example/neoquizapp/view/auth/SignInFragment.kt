package com.example.neoquizapp.view.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.FragmentSignInBinding
import com.example.neoquizapp.viewModel.AuthViewModel.LoginViewModel

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupValidation()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
        binding.loginBtn.setOnClickListener {
            login()
        }
        binding.btnForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_resetPasswordFragment)
        }
        binding.imageView5.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun setupValidation() {
        val textWatchers = arrayOf(
            binding.etLogin,
            binding.etPassword,
        )

        for (watchedText in textWatchers) {
            watchedText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val login = binding.etLogin.text.toString()
                    val password = binding.etPassword.text.toString()
                    binding.loginBtn.isEnabled = login.isNotEmpty() && password.isNotEmpty()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    private fun login() {
        val login = binding.etLogin.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.login(
            login,
            password,
            onSuccess = {
                findNavController().navigate(R.id.mainPageFragment)
            },
            onError = {
                binding.incorrectDataMsg.visibility = View.VISIBLE
                binding.etLogin.setBackgroundResource(R.drawable.rounded_et_error)
                binding.etPassword.setBackgroundResource(R.drawable.rounded_et_error)
            }
        )
    }

    private fun togglePasswordVisibility() {
        val isPasswordVisible = binding.etPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod = if (isPasswordVisible) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.etPassword.transformationMethod = newTransformationMethod
        binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)
        if (isPasswordVisible) {
            binding.imageView5.setImageResource(R.drawable.icons)
        } else {
            binding.imageView5.setImageResource(R.drawable.icon_opened_eye)
        }
    }
}