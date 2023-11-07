package com.example.neoquizapp.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.CardQuizBinding
import com.example.neoquizapp.model.mainModel.Quiz
import com.github.islamkhsh.CardSliderAdapter
import com.google.android.material.card.MaterialCardView

class SliderAdapter(private var quizzes: List<Quiz>) :
    CardSliderAdapter<SliderAdapter.SliderViewHolder>() {
    private var selectedQuizId: Int? = null

    private val cardBackgrounds = arrayOf(
        R.color.color_1,
        R.color.color_2,
        R.color.color_3,
        R.color.color_4,
        R.color.color_5
    )

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: SliderAdapter.OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(quiz: Quiz)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = CardQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SliderViewHolder(binding)
    }

    override fun bindVH(holder: SliderViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.bind(quiz)

        selectedQuizId = quiz.id
    }

    fun getSelectedQuizId(): Int? {
        return selectedQuizId
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

    inner class SliderViewHolder(private val binding: CardQuizBinding) :
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

        fun bind(quiz: Quiz) {
            val position = cardBackgrounds.indices.random()
            val color = cardBackgrounds[position]

            val shadowDrawable = GradientDrawable()
            shadowDrawable.setColor(ContextCompat.getColor(binding.root.context, color))
            shadowDrawable.cornerRadius =  10f

            val cardView = binding.layout
            cardView.background = shadowDrawable

            cardView.cardElevation = 30f
            cardView.preventCornerOverlap = true
            cardView.radius = 10f

            if (quiz.title != null) {
                binding.quizTitle.text = quiz.title
                binding.quizQuestion.text = "${quiz.question_count} вопросов"
                if (quiz.is_completed) {
                    binding.imgPassed?.visibility = View.VISIBLE
                }
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
