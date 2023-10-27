package com.example.neoquizapp.view.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.FragmentResetPasswordBinding
import com.example.neoquizapp.viewModel.AuthViewModel.SendCodeViewModel

class ResetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding
    private val viewModel: SendCodeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupValidation()
        setupNavigation()
    }


    private fun setupNavigation() {
        binding.nextBtn.setOnClickListener {
            sendCode()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_resetPasswordFragment_to_signInFragment)
        }
    }

    private fun sendCode() {
        val username = binding.etLogin.text.toString()
        val email = binding.etMail.text.toString()

        val bundle = Bundle()
        bundle.putString("username", username)
        bundle.putString("email", email)

        viewModel.sendCode(
            username,
            email,
            onSuccess = {
                findNavController().navigate(R.id.action_resetPasswordFragment_to_codeVerificationFragment, bundle)
            }
        )
    }

    private fun setupValidation() {
        val textWatchers = arrayOf(
            binding.etLogin,
            binding.etMail,
        )

        for (watchedText in textWatchers) {
            watchedText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val login = binding.etLogin.text.toString()
                    val mail = binding.etMail.text.toString()
                    binding.nextBtn.isEnabled = login.isNotEmpty() && mail.isNotEmpty()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }
}