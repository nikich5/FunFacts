package ru.nikich5.funfacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.nikich5.funfacts.dto.Fact
import ru.nikich5.funfacts.repository.FactRepository
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val repository: FactRepository
) : ViewModel() {

    private val cached = repository
        .data
        .cachedIn(viewModelScope)

    val data: Flow<PagingData<Fact>> = cached
}