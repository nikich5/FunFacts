package ru.nikich5.funfacts.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ru.nikich5.funfacts.api.ApiService
import ru.nikich5.funfacts.dao.FactDao
import ru.nikich5.funfacts.db.AppDb
import ru.nikich5.funfacts.entity.FactEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FactRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val dao: FactDao,
    private val db: AppDb
) : RemoteMediator<Int, FactEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FactEntity>
    ): MediatorResult {
        try {
            val response = when (loadType) {
                LoadType.REFRESH -> apiService.getFacts(state.config.initialLoadSize)
                LoadType.PREPEND -> return MediatorResult.Success(false)
                LoadType.APPEND -> apiService.getFacts(state.config.pageSize)
            }

            val body = response.body() ?: throw Exception()
            val factList = body.map { FactEntity(text = it) }
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    dao.removeAll()
                }
                dao.insert(factList)
            }
            return MediatorResult.Success(body.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}