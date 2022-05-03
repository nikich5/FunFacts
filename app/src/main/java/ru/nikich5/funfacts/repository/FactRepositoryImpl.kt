package ru.nikich5.funfacts.repository

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.nikich5.funfacts.api.ApiService
import ru.nikich5.funfacts.dao.FactDao
import ru.nikich5.funfacts.db.AppDb
import ru.nikich5.funfacts.dto.Fact
import ru.nikich5.funfacts.entity.FactEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Exception

@Singleton
class FactRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: FactDao,
    private val db: AppDb
) : FactRepository {

    @OptIn(ExperimentalPagingApi::class)
    override val data: Flow<PagingData<Fact>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = FactRemoteMediator(apiService, dao, db),
        pagingSourceFactory = dao::pagingSource
    ).flow.map { pagingData ->
        pagingData.map(FactEntity::toDto)
    }

    override suspend fun removeById(id: Long) {
        try {
            dao.removeById(id)
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}