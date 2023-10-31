package com.example.neoquizapp.view.mainPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.FragmentFilterBinding
import com.example.neoquizapp.viewModel.MainViewModel.FilterViewModel

class FilterFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private var filter: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        getFilter()
        binding.closeBtn.setOnClickListener {
            findNavController().navigate(R.id.allArticlesFragment)
        }
        binding.addBtn.setOnClickListener {
            val bundle = Bundle()
            filter?.let { category ->
                bundle.putInt("category", category)
            }
            findNavController().navigate(R.id.allArticlesFragment, bundle)
        }
        binding.btnReset.setOnClickListener {
            clearFilter()
        }
    }

    private fun getFilter() {
        val buttons = listOf(
            Pair(binding.btnHistory, 1),
            Pair(binding.btnLiterature, 3),
            Pair(binding.btnPhilosophy, 2),
            Pair(binding.btnPsychology, 4),
            Pair(binding.btnArt, 5),
            Pair(binding.btnMusic, 6),
            Pair(binding.btnKino, 7)
        )
        buttons.forEach { (button, category) ->
            button.setOnClickListener {
                filter = category
                button.setImageResource(R.drawable.state_true)
                updateAddButtonState()
            }
        }
    }

    private fun clearFilter() {
        val buttons = listOf(
            Pair(binding.btnHistory, 1),
            Pair(binding.btnLiterature, 3),
            Pair(binding.btnPhilosophy, 2),
            Pair(binding.btnPsychology, 4),
            Pair(binding.btnArt, 5),
            Pair(binding.btnMusic, 6),
            Pair(binding.btnKino, 7)
        )
        filter = null
        buttons.forEach { (button) ->
            button.setBackgroundResource(R.drawable.frame_35)
        }
    }

    private fun updateAddButtonState() {
        binding.addBtn.isEnabled = filter != null
    }

}