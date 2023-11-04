package com.example.neoquizapp.view.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoquizapp.R
import com.example.neoquizapp.adapters.ArticleAdapter
import com.example.neoquizapp.databinding.FragmentAllArticlesBinding
import com.example.neoquizapp.model.mainModel.Article
import com.example.neoquizapp.viewModel.MainViewModel.FilterViewModel
import com.example.neoquizapp.viewModel.MainViewModel.GetArticlesViewModel

class AllArticlesFragment : Fragment() {
    private lateinit var binding: FragmentAllArticlesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArticleAdapter
    private val viewModel: GetArticlesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllArticlesBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerViewArticles
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ArticleAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val category = arguments?.getInt("category", -1)
        if (category != null) {
            getFilterArticles(category)
            binding.filterSelectedImg.visibility = View.VISIBLE
        } else {
            getArticles()
            binding.filterSelectedImg.visibility = View.GONE
        }
        setupNavigation()
        search()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.mainPageFragment)
        }
        adapter.setOnItemClickListener(object : ArticleAdapter.OnItemClickListener {
            override fun onItemClick(article: Article) {
                val bundle = Bundle()
                bundle.putInt("id", article.id)
                findNavController().navigate(R.id.detailArticleFragment, bundle)
            }
        })
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(R.id.action_allArticlesFragment_to_filterFragment)
        }
    }

    private fun getArticles() {
        viewModel.getAllArticles() {
                article -> adapter.updateData(article)
        }
    }

    private fun getFilterArticles(category: Int) {
        viewModel.getArticlesByCategory(category) {
                article -> adapter.updateData(article)
        }
    }

    private fun search() {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.getArticlesBySearch(newText) { article ->
                        if (article.isEmpty()) {
                            binding.searchText.visibility = View.VISIBLE
                            binding.searchImg.visibility = View.VISIBLE
                            binding.recyclerViewArticles.visibility = View.GONE
                        } else {
                            binding.searchText.visibility = View.GONE
                            binding.searchImg.visibility = View.GONE
                            binding.recyclerViewArticles.visibility = View.VISIBLE

                            adapter.updateData(article)
                        }
                    }
                }
                return true
            }
        })
    }
}