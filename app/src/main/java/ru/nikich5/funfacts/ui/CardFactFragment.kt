package ru.nikich5.funfacts.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nikich5.funfacts.databinding.CardFactBinding
import ru.nikich5.funfacts.viewmodel.FactViewModel

@AndroidEntryPoint
class CardFactFragment : Fragment() {
    private val factViewModel: FactViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CardFactBinding.inflate(inflater, container, false)
        val fact = factViewModel.currentFact
        binding.apply {
            factText.text = fact.text

            share.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, fact.text)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, "")
                startActivity(shareIntent)
            }

            delete.setOnClickListener {
                factViewModel.removeById(fact.id)
                findNavController().navigateUp()
            }
        }

        return binding.root
    }
}