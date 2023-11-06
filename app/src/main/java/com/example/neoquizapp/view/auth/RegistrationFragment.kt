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
import com.example.neoquizapp.databinding.FragmentRegistrationBinding
import com.example.neoquizapp.viewModel.AuthViewModel.RegisterViewModel

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupValidation()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.registerBtn.setOnClickListener {
            register()
        }
        binding.imageView2.setOnClickListener {
            togglePasswordVisibility()
        }
        binding.imageView3.setOnClickListener {
            togglePasswordRepeatVisibility()
        }
    }

    private fun setupValidation() {
        val textWatchers = arrayOf(
            binding.etName,
            binding.etLogin,
            binding.etPassword,
            binding.etRepeatPassword
        )

        for (watchedText in textWatchers) {
            watchedText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val name = binding.etName.text.toString()
                    val login = binding.etLogin.text.toString()
                    val password = binding.etPassword.text.toString()
                    val passwordRepeat = binding.etRepeatPassword.text.toString()
                    binding.registerBtn.isEnabled = name.isNotEmpty() && login.isNotEmpty() && password.isNotEmpty() && passwordRepeat.isNotEmpty()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    private fun register() {
        val name = binding.etName.text.toString()
        val login = binding.etLogin.text.toString()
        val password = binding.etPassword.text.toString()
        val passwordRepeat = binding.etRepeatPassword.text.toString()

        viewModel.register(
            login,
            name,
            password,
            passwordRepeat,
            onSuccess = {
                findNavController().navigate(R.id.mainPageFragment)
            },
            onError = {
                if (name.isEmpty()) {
                    binding.nameValid.visibility = View.VISIBLE
                }
                if (password != passwordRepeat) {
                    binding.etPassword.setBackgroundResource(R.drawable.rounded_et_error)
                    binding.etRepeatPassword.setBackgroundResource(R.drawable.rounded_et_error)
                    binding.passwordsValid.visibility = View.VISIBLE
                }
            }
        )
    }

    private fun togglePasswordVisibility() {
        val isPasswordVisible = binding.etPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod = if (isPasswordVisible) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.etPassword.transformationMethod = newTransformationMethod
        binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)

        if (isPasswordVisible) {
            binding.imageView2.setImageResource(R.drawable.icons)
        } else {
            binding.imageView2.setImageResource(R.drawable.icon_opened_eye)
        }
    }

    private fun togglePasswordRepeatVisibility() {
        val isPasswordVisible2 = binding.etRepeatPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod2 = if (isPasswordVisible2) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.etRepeatPassword.transformationMethod = newTransformationMethod2
        binding.etRepeatPassword.setSelection(binding.etRepeatPassword.text?.length ?: 0)

        if (isPasswordVisible2) {
            binding.imageView3.setImageResource(R.drawable.icons)
        } else {
            binding.imageView3.setImageResource(R.drawable.icon_opened_eye)
        }
    }
}
