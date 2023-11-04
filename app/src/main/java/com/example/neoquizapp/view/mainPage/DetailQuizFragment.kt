package com.example.neoquizapp.view.mainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.FragmentDetailQuizBinding
import com.example.neoquizapp.viewModel.MainViewModel.GetQuizViewModel

class DetailQuizFragment : Fragment() {
    private lateinit var binding: FragmentDetailQuizBinding
    private val viewModel: GetQuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
            getQuiz(id)
        }
        if (id != null) {
            setupNavigation(id)
        }
    }

    private fun setupNavigation(id: Int) {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailQuizFragment_to_allQuizzesFragment)
        }
        binding.startQuiz.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.action_detailQuizFragment_to_questionsPageFragment, bundle)
        }
    }

    private fun getQuiz(id: Int) {
        viewModel.getQuiz(id) {
            quiz ->
            run {
                binding.quizTitle.text = quiz.title
                binding.quizText.text = quiz.description
                when (quiz.title) {
                    "История" -> {
                        binding.quizImage.setImageResource(R.drawable.history_quiz_img)
                    }

                    "Философия" -> {
                        binding.quizImage.setImageResource(R.drawable.pholosophy_img)
                    }

                    "Литература" -> {
                        binding.quizImage.setImageResource(R.drawable.literature_quiz_img)
                    }

                    "Психология" -> {
                        binding.quizImage.setImageResource(R.drawable.psychology_quiz_img)
                    }

                    "Исскуство" -> {
                        binding.quizImage.setImageResource(R.drawable.pholosophy_img)
                    }

                    "Музыка" -> {
                        binding.quizImage.setImageResource(R.drawable.kino_img)
                    }

                    "Кино" -> {
                        binding.quizImage.setImageResource(R.drawable.kino_img)
                    }
                }

            }
        }
    }
}