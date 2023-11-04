package com.example.neoquizapp.view.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.FragmentQuestionsPageBinding
import com.example.neoquizapp.model.mainModel.Question
import com.example.neoquizapp.viewModel.MainViewModel.GetQuizQuestions
import kotlin.math.min

class QuestionsPageFragment : Fragment() {
    private lateinit var binding: FragmentQuestionsPageBinding
    private var questions: List<Question>? = null
    private var currentQuestionIndex = 0
    private val questionAnswerMap = mutableMapOf<String, Int>()
    private lateinit var progressBar: ProgressBar
    private val viewModel: GetQuizQuestions by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionsPageBinding.inflate(inflater, container, false)
        progressBar = binding.progressBar2
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
            getQuestion(id)
        }

        progressBar.max = 10

        binding.nextQuiz.setOnClickListener {
            if (questions != null && currentQuestionIndex < questions!!.size - 1) {
                currentQuestionIndex++
                if (id != null) {
                    displayQuestion(currentQuestionIndex)
                }
                progressBar.progress = currentQuestionIndex
                binding.numOfQuestion.text = "Вопрос ${currentQuestionIndex + 1} из 10"
            } else {
                if (id != null) {
                    println("map = $questionAnswerMap")
                    viewModel.submitQuiz(questionAnswerMap, id) { submissionResponse ->
                        val numOfQuestion = submissionResponse.correct_answers
                        val bundle = Bundle()
                        bundle.putInt("questionsNum", numOfQuestion)
                        findNavController().navigate(R.id.finishedQuizFragment, bundle)
                    }
                }
            }
        }
    }

    private fun displayQuestion(index: Int) {
        val options = arrayOf(
            binding.btnFirst,
            binding.btnSecond,
            binding.btnThird,
            binding.btnFourth
        )

        if (questions != null && index < questions!!.size) {
            val question = questions!![index]
            binding.questionText.text = question.title

            for (i in 0 until min(options.size, question.answer.size)) {
                val answer = question.answer[i]
                options[i].text = answer.answer_text

                options[i].isEnabled = true // Enable the button
                options[i].setBackgroundResource(R.drawable.rounded_btn_transparent)

                options[i].setOnClickListener {
                    if (answer.is_right) {
                        options[i].setBackgroundResource(R.drawable.btn_correct_answer)
                    } else {
                        options[i].setBackgroundResource(R.drawable.btn_wrong_answer)
                    }

                    questionAnswerMap[question.id.toString()] = answer.id
                    disableAnswerButtons(options)
                }
            }
        } else {
        }
    }


    private fun disableAnswerButtons(options: Array<AppCompatButton>) {
        for (button in options) {
            button.isEnabled = false
        }
    }

    private fun setQuestions(questionsList: List<Question>) {
        questions = questionsList
        currentQuestionIndex = 0
        displayQuestion(currentQuestionIndex)
        binding.numOfQuestion.text = "Вопрос ${currentQuestionIndex + 1} из 10"

        questionAnswerMap.clear()
    }

    private fun getQuestion(id: Int) {
        viewModel.getQuizQuestions(id) { questionsList ->
            setQuestions(questionsList)
        }
    }
}
