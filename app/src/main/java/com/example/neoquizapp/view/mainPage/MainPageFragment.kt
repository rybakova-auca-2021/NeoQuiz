package com.example.neoquizapp.view.mainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoquizapp.R
import com.example.neoquizapp.adapters.ArticleAdapter
import com.example.neoquizapp.adapters.ArticleMainAdapter
import com.example.neoquizapp.adapters.QuizMainAdapter
import com.example.neoquizapp.databinding.FragmentMainPageBinding
import com.example.neoquizapp.model.mainModel.Article
import com.example.neoquizapp.viewModel.MainViewModel.GetArticlesViewModel
import com.example.neoquizapp.viewModel.MainViewModel.GetQuizzesViewModel


class MainPageFragment : Fragment() {
    private lateinit var binding: FragmentMainPageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewQuiz: RecyclerView
    private lateinit var adapter: ArticleMainAdapter
    private lateinit var quizAdapter: QuizMainAdapter
    private val viewModel: GetArticlesViewModel by viewModels()
    private val quizViewModel: GetQuizzesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerViewArticles
        recyclerViewQuiz = binding.recyclerViewQuiz
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ArticleMainAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        quizAdapter = QuizMainAdapter(emptyList())
        recyclerViewQuiz.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewQuiz.adapter = quizAdapter

        getArticles()
        getQuizzes()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.arrowArticles.setOnClickListener {
            findNavController().navigate(R.id.allArticlesFragment)
        }
        binding.imageView7.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_profileFragment)
        }
        adapter.setOnItemClickListener(object : ArticleMainAdapter.OnItemClickListener {
            override fun onItemClick(article: Article) {
                val bundle = Bundle()
                bundle.putInt("id", article.id)
                findNavController().navigate(R.id.detailArticleFragment, bundle)
            }
        })
        binding.arrowQuizzes.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_allQuizzesFragment)
        }
    }

    private fun getArticles() {
        viewModel.getAllArticles() {
            article -> adapter.updateData(article)
        }
    }

    private fun getQuizzes() {
        quizViewModel.getQuizzes {
                article -> quizAdapter.updateData(article)
        }
    }
}