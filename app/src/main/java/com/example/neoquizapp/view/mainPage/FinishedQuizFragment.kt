package com.example.neoquizapp.view.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.FragmentFinishedQuizBinding

class FinishedQuizFragment : Fragment() {
    private lateinit var binding: FragmentFinishedQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinishedQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val questionsNum = arguments?.getInt("questionsNum")
        binding.questionNum.text = "$questionsNum вопросов из 10"
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.returnBtn.setOnClickListener {
            findNavController().navigate(R.id.mainPageFragment)
        }
    }
}