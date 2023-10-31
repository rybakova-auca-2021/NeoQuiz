package com.example.neoquizapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.neoquizapp.R
import com.example.neoquizapp.databinding.CardArticleMainBinding
import com.example.neoquizapp.model.mainModel.Article

class ArticleMainAdapter(private var articles: List<Article>) :
    RecyclerView.Adapter<ArticleMainAdapter.ArticleViewHolder>() {

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

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = CardArticleMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun updateData(newList: List<Article>) {
        val diffResult = DiffUtil.calculateDiff(
            ProductDiffCallback(
                articles,
                newList
            )
        )
        articles = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ArticleViewHolder(private val binding: CardArticleMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = articles[position]
                    itemClickListener?.onItemClick(clickedItem)
                }
            }
        }

        fun bind(article: Article) {
            val position = cardBackgrounds.indices.random()
            binding.layout.setBackgroundResource(cardBackgrounds[position])

            binding.articleName.text = article.title
            val category = article.category
            if (category != null) {
                binding.hashtagArticle.text = category.name
            } else {
                binding.hashtagArticle.text = "Unknown Category"
            }
            when (category.name) {
                "История" -> {
                    binding.articleImage.setImageResource(R.drawable.history_img)
                }
                "Философия" -> {
                    binding.articleImage.setImageResource(R.drawable.pholosophy_img)
                }
                "Литература" -> {
                    binding.articleImage.setImageResource(R.drawable.literature_img)
                }
                "Психология" -> {
                    binding.articleImage.setImageResource(R.drawable.literature_img)
                }
                "Исскуство" -> {
                    binding.articleImage.setImageResource(R.drawable.pholosophy_img)
                }
                "Музыка" -> {
                    binding.articleImage.setImageResource(R.drawable.kino_img)
                }
                "Кино" -> {
                    binding.articleImage.setImageResource(R.drawable.kino_img)
                }
            }
        }
    }

    class ProductDiffCallback(
        private val oldList: List<Article>,
        private val newList: List<Article>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
