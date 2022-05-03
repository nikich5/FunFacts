package ru.nikich5.funfacts.adapter

import androidx.paging.LoadStateAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ru.nikich5.funfacts.databinding.LoadStateBinding

class PagingLoadStateAdapter(
    private val onInteractionListener: OnInteractionListener,
) : LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

    interface OnInteractionListener {
        fun onRetry() {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onInteractionListener
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadStateViewHolder(
        private val binding: LoadStateBinding,
        private val onInteractionListener: OnInteractionListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.apply {
                progress.isVisible = loadState is LoadState.Loading
                retry.isVisible = loadState is LoadState.Error

                retry.setOnClickListener {
                    onInteractionListener.onRetry()
                }
            }
        }
    }
}