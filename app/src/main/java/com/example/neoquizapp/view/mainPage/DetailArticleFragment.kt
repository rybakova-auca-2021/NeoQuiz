package com.example.neoquizapp.view.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.FragmentDetailArticleBinding
import com.example.neoquizapp.viewModel.MainViewModel.GetArticlesViewModel

class DetailArticleFragment : Fragment() {
    private lateinit var binding: FragmentDetailArticleBinding
    private val viewModel: GetArticlesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
            getArticle(id)
        }
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.allArticlesFragment)
        }
    }

    private fun getArticle(id: Int) {
        viewModel.getAllArticles() { article ->
            val desiredArticle = article[id - 1]
            binding.articleHashtag.text = "#${desiredArticle.category.name}"
            binding.articleTime.text = "${desiredArticle.reading_time_minutes} minutes"
            binding.articleName.text = desiredArticle.title
            binding.articleText.text = desiredArticle.text
        }
    }
}