package com.example.neoquizapp.view.auth

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.FragmentCreateNewPasswordBinding
import com.example.neoquizapp.viewModel.AuthViewModel.ChangePasswordViewModel

class CreateNewPasswordFragment : Fragment() {
    private lateinit var binding: FragmentCreateNewPasswordBinding
    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupValidation()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.loginBtn.setOnClickListener {
            changePassword()
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_createNewPasswordFragment_to_codeVerificationFragment)
        }
        binding.checkPassword.setOnClickListener {
            togglePasswordVisibility()
        }
        binding.checkRepeatPassword.setOnClickListener {
            togglePasswordRepeatVisibility()
        }
    }

    private fun changePassword() {
        val password = binding.etPassword.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()

        viewModel.changePassword(
            password,
            repeatPassword,
            onSuccess = {
                showDialog()
            },
            onError = {
                if (password != repeatPassword) {
                    binding.incorrectDataMsg.visibility = View.VISIBLE
                    binding.etPassword.setBackgroundResource(R.drawable.rounded_et_error)
                    binding.etRepeatPassword.setBackgroundResource(R.drawable.rounded_et_error)
                }
            }
        )
    }

    private fun setupValidation() {
        val textWatchers = arrayOf(
            binding.etPassword,
            binding.etRepeatPassword
        )

        for (watchedText in textWatchers) {
            watchedText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val password = binding.etPassword.text.toString()
                    val passwordRepeat = binding.etRepeatPassword.text.toString()
                    binding.loginBtn.isEnabled = passwordRepeat.isNotEmpty() && password.isNotEmpty()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    private fun togglePasswordVisibility() {
        val isPasswordVisible = binding.etPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod = if (isPasswordVisible) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.etPassword.transformationMethod = newTransformationMethod
        binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)
    }

    private fun togglePasswordRepeatVisibility() {
        val isPasswordVisible2 = binding.etRepeatPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod2 = if (isPasswordVisible2) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.etRepeatPassword.transformationMethod = newTransformationMethod2
        binding.etRepeatPassword.setSelection(binding.etRepeatPassword.text?.length ?: 0)
    }

    private fun showDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_password_changed, null)
        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogView)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT // Width of the dialog window
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT // Height of the dialog window
        myDialog.window?.attributes = layoutParams

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        dialogView.setOnClickListener {
            myDialog.dismiss()
            findNavController().navigate(R.id.signInFragment)
        }
    }

}