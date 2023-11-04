package com.example.neoquizapp.view.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.adapters.SliderAdapter
import com.example.neoquizapp.databinding.FragmentAllQuizzesBinding
import com.example.neoquizapp.model.mainModel.Quiz
import com.example.neoquizapp.viewModel.MainViewModel.GetQuizzesViewModel
import com.github.islamkhsh.CardSliderAdapter
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

class AllQuizzesFragment : Fragment() {
    private lateinit var binding: FragmentAllQuizzesBinding
    private lateinit var adapter: SliderAdapter
    private lateinit var slider: CarouselRecyclerview
    private val viewModel: GetQuizzesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllQuizzesBinding.inflate(inflater, container, false)
        slider = binding.carouselRecyclerview
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SliderAdapter(emptyList())
        slider.adapter = adapter
        slider.set3DItem(true)
        slider.setInfinite(true)
        slider.setAlpha(true)

        adapter.setOnItemClickListener(object : SliderAdapter.OnItemClickListener {
            override fun onItemClick(quiz: Quiz) {
                val bundle = Bundle()
                bundle.putInt("id", quiz.id)
                findNavController().navigate(R.id.detailQuizFragment, bundle)
            }
        })

        getQuizzes()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_allQuizzesFragment_to_mainPageFragment)
        }
        binding.registerBtn.setOnClickListener {
            val selectedQuizId = adapter.getSelectedQuizId()
            if (selectedQuizId != null) {
                val bundle = Bundle()
                bundle.putInt("id", selectedQuizId)
                findNavController().navigate(R.id.questionsPageFragment, bundle)
            }
        }
    }

    private fun getQuizzes() {
        viewModel.getQuizzes {
            quiz -> adapter.updateData(quiz)
        }
    }
}