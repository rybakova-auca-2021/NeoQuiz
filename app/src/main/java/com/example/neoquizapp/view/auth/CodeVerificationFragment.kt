package com.example.neoquizapp.view.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.FragmentCodeVerificationBinding
import com.example.neoquizapp.viewModel.AuthViewModel.SendCodeViewModel
import com.example.neoquizapp.viewModel.AuthViewModel.VerifyCodeViewModel

class CodeVerificationFragment : Fragment() {
    private lateinit var binding: FragmentCodeVerificationBinding
    private val viewModel: VerifyCodeViewModel by viewModels()
    private val sendCodeViewModel: SendCodeViewModel by viewModels()
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 60000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCodeVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString("username")
        val email = arguments?.getString("email")

        if (email != null) {
            changeText(email)
        }
        setupValidation()
        if (username != null && email != null) {
            setupNavigation(username, email)
        }

        startCountdownTimer()
    }

    @SuppressLint("SetTextI18n")
    private fun changeText(email: String) {
        binding.textView.text = "На вашу почту $email было отправлено письмо с кодом для восстановления пароля."
    }

    private fun setupNavigation(username: String, email: String) {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_codeVerificationFragment_to_resetPasswordFragment)
        }
        binding.loginBtn.setOnClickListener {
            verifyCode(username)
        }
        binding.btnSendAgain.setOnClickListener {
            sendCode(username, email)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun verifyCode(username: String) {
        val code = binding.pinView.text.toString()
        viewModel.verifyCode(
            username,
            code.toInt(),
            onSuccess = {
                findNavController().navigate(R.id.action_codeVerificationFragment_to_createNewPasswordFragment)
            },
            onError = {
                binding.errorMsg.visibility = View.VISIBLE
                val errorColor = ContextCompat.getColor(requireContext(), R.color.error)
                binding.pinView.setLineColor(errorColor)
            }
        )
    }

    private fun sendCode(username: String, email: String) {
        sendCodeViewModel.sendCode(
            username,
            email,
            onSuccess = {
                println("code was sent")
            }
        )
    }
    private fun setupValidation() {
        val textWatchers = arrayOf(
            binding.pinView
        )

        for (watchedText in textWatchers) {
            watchedText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val pin = binding.pinView.text.toString()
                    binding.loginBtn.isEnabled = pin.isNotEmpty()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    private fun startCountdownTimer() {
        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                binding.btnSendAgain.text = "Отправить код повторно 0:${millisUntilFinished / 1000}"
            }

            @SuppressLint("ResourceAsColor")
            override fun onFinish() {
                timeRemaining = 0
                binding.btnSendAgain.isEnabled = true
                binding.btnSendAgain.text = "Отправить код повторно"
                binding.btnSendAgain.setTextColor(R.color.black)
            }
        }

        countDownTimer?.start()
    }

}