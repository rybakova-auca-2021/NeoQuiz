package com.example.neoquizapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.CardQuizMainBinding
import com.example.neoquizapp.model.mainModel.Quiz

class QuizMainAdapter(private var quizzes: List<Quiz>) :
    RecyclerView.Adapter<QuizMainAdapter.QuizViewHolder>() {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = CardQuizMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.bind(quiz)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    fun updateData(newList: List<Quiz>) {
        val diffResult = DiffUtil.calculateDiff(
            ProductDiffCallback(
                quizzes,
                newList
            )
        )
        quizzes = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class QuizViewHolder(private val binding: CardQuizMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: Quiz) {
            val position = cardBackgrounds.indices.random()
            binding.layout.setBackgroundResource(cardBackgrounds[position])

            if (quiz.title != null) {
                binding.articleCategory.text = quiz.title
                binding.numQuestions.text = "${quiz.question_count} вопросов"
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

    class ProductDiffCallback(
        private val oldList: List<Quiz>,
        private val newList: List<Quiz>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
