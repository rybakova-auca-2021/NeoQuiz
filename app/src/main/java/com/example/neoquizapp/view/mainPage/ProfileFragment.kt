
package com.example.neoquizapp.view.mainPage

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoquizapp.R
import com.example.neoquizapp.adapters.PassedQuizzesAdapter
import com.example.neoquizapp.adapters.SliderAdapter
import com.example.neoquizapp.databinding.FragmentProfileBinding
import com.example.neoquizapp.model.QuizProfile
import com.example.neoquizapp.model.mainModel.Quiz
import com.example.neoquizapp.utils.Utils
import com.example.neoquizapp.viewModel.MainViewModel.GetProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: GetProfileViewModel by viewModels()
    private lateinit var adapter: PassedQuizzesAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfile()
        adapter = PassedQuizzesAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        setupNavigation()
    }


    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_mainPageFragment)
        }
        binding.logoutBtn.setOnClickListener {
            showDialog()
        }
        adapter.setOnItemClickListener(object : PassedQuizzesAdapter.OnItemClickListener {
            override fun onItemClick(quiz: QuizProfile) {
                val bundle = Bundle()
                bundle.putInt("id", quiz.id)
                findNavController().navigate(R.id.questionsPageFragment, bundle)
            }
        })
    }

    private fun getProfile() {
        profileViewModel.getProfile {
            profileData ->
            binding.name.text = profileData.user.name
            binding.username.text = profileData.user.username
            adapter.updateData(profileData.completed_quizzes)
        }
    }

    private fun showDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_logout, null)
        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogView)

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val closeBtn = myDialog.findViewById<TextView>(R.id.btn_clode)
        val logoutBtn = myDialog.findViewById<TextView>(R.id.btn_logout)

        closeBtn.setOnClickListener {
            myDialog.dismiss()
        }
        logoutBtn.setOnClickListener {
            myDialog.dismiss()
            Utils.access = null
            findNavController().navigate(R.id.signInFragment)
        }

        dialogView.setOnClickListener {
            myDialog.dismiss()
        }
    }
}