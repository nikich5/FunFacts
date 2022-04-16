package ru.nikich5.funfacts.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.nikich5.funfacts.databinding.CardFactBinding

@AndroidEntryPoint
class CardFactFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CardFactBinding.inflate(inflater, container, false)

        return binding.root
    }
}