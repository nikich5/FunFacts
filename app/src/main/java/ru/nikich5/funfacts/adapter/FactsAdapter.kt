package ru.nikich5.funfacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.nikich5.funfacts.databinding.CardFactBinding
import ru.nikich5.funfacts.dto.Fact

interface OnInteractionListener {
    fun clickedOnCard(fact: Fact) {}
    fun clickedOnShare(fact: Fact) {}
    fun clickedOnDelete(fact: Fact) {}
}

class FactsAdapter(
    private val onInteractionListener: OnInteractionListener
) : PagingDataAdapter<Fact, FactViewHolder>(FactDiffCallback()) {
    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val fact = getItem(position) ?: return
        holder.bind(fact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val binding = CardFactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FactViewHolder(binding, onInteractionListener)
    }

}

class FactViewHolder(
    private val binding: CardFactBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(fact: Fact) {
        binding.apply {
            title.text = "Fun fact №${fact.id}"
            factText.text = fact.text

            card.setOnClickListener {
                onInteractionListener.clickedOnCard(fact)
            }
            factText.setOnClickListener {
                onInteractionListener.clickedOnCard(fact)
            }
            share.setOnClickListener {
                onInteractionListener.clickedOnShare(fact)
            }
            delete.setOnClickListener {
                onInteractionListener.clickedOnDelete(fact)
            }

        }
    }
}

class FactDiffCallback : DiffUtil.ItemCallback<Fact>() {
    override fun areItemsTheSame(oldItem: Fact, newItem: Fact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Fact, newItem: Fact): Boolean {
        return oldItem == newItem
    }

}

