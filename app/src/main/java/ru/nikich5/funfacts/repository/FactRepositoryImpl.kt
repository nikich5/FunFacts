package ru.nikich5.funfacts.repository

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.nikich5.funfacts.api.ApiService
import ru.nikich5.funfacts.dao.FactDao
import ru.nikich5.funfacts.dto.Fact
import ru.nikich5.funfacts.entity.FactEntity
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: FactDao
) : FactRepository {

    @OptIn(ExperimentalPagingApi::class)
    override val data: Flow<PagingData<Fact>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = FactRemoteMediator(apiService, dao),
        pagingSourceFactory = dao::pagingSource
    ).flow.map { pagingData ->
        pagingData.map(FactEntity::toDto)
    }
}