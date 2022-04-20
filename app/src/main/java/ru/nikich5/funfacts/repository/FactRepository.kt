package ru.nikich5.funfacts.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.nikich5.funfacts.dto.Fact

interface FactRepository {
    val data: Flow<PagingData<Fact>>
    suspend fun removeById(id: Long)
}