package com.example.neoquizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.CardPassedQuizBinding
import com.example.neoquizapp.databinding.CardQuizMainBinding
import com.example.neoquizapp.model.QuizProfile
import com.example.neoquizapp.model.mainModel.Quiz

class PassedQuizzesAdapter(private var quizzes: List<QuizProfile>) :
    RecyclerView.Adapter<PassedQuizzesAdapter.QuizViewHolder>() {

    private val cardBackgrounds = arrayOf(
        R.drawable.card1,
        R.drawable.card2,
        R.drawable.card3,
        R.drawable.card4,
        R.drawable.card5,
        R.drawable.card6,
        R.drawable.card7,
        R.drawable.card8
    )

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: PassedQuizzesAdapter.OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(quiz: QuizProfile)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = CardPassedQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.bind(quiz)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    fun updateData(newList: List<QuizProfile>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffCallback(quizzes, newList))
        quizzes = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class QuizViewHolder(private val binding: CardPassedQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = quizzes[position]
                    itemClickListener?.onItemClick(clickedItem)
                }
            }
        }

        fun bind(quiz: QuizProfile) {
            val position = cardBackgrounds.indices.random()
            binding.layout.setBackgroundResource(cardBackgrounds[position])
            binding.articleName.text = "Квиз '${quiz.title}'"
        }
    }

    class ProductDiffCallback(
        private val oldList: List<QuizProfile>,
        private val newList: List<QuizProfile>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
