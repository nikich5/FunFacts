package ru.nikich5.funfacts.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.nikich5.funfacts.R
import ru.nikich5.funfacts.adapter.FactsAdapter
import ru.nikich5.funfacts.adapter.OnInteractionListener
import ru.nikich5.funfacts.databinding.FragmentFeedBinding
import ru.nikich5.funfacts.dto.Fact
import ru.nikich5.funfacts.viewmodel.FactViewModel

@AndroidEntryPoint
class FeedFragment : Fragment() {
    private val factViewModel: FactViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = FactsAdapter(object : OnInteractionListener {
            override fun clickedOnCard(fact: Fact) {
                findNavController().navigate(R.id.action_feedFragment_to_cardFactFragment)
            }

            override fun clickedOnShare(fact: Fact) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, fact.text)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, "")
                startActivity(shareIntent)
            }
        })
        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            factViewModel.data.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                binding.swipeRefresh.isRefreshing =
                    it.refresh is LoadState.Loading || it.append is LoadState.Loading
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }

        return binding.root
    }
}