package com.sawrose.marvelapp.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.sawrose.marvelapp.core.common.network.Dispatcher
import com.sawrose.marvelapp.core.common.network.MarvelDispatchers.IO
import com.sawrose.marvelapp.core.data.Synchronizer
import com.sawrose.marvelapp.core.data.repository.MarvelRepository
import com.sawrose.marvelapp.core.datastore.ChangeListVersion
import com.sawrose.marvelapp.core.datastore.MarvelPreferencesDataSource
import com.sawrose.marvelapp.sync.initializer.SyncConstraints
import com.sawrose.marvelapp.sync.initializer.syncForegroundInfo
import com.sawrose.marvelapp.sync.status.SyncSubscriber
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    @Dispatcher(IO) private val IODispatcher: CoroutineDispatcher,
    private val preferences: MarvelPreferencesDataSource,
    private val characterRepository: MarvelRepository,
    private val syncSubscribe: SyncSubscriber
) : CoroutineWorker(appContext, workerParams),Synchronizer {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(IODispatcher){
        syncSubscribe.subscribe()
        // First sync the repositories in parallel
        val syncedSuccessfully = awaitAll(
            async { characterRepository.sync() },
        ).all { it }

        if (syncedSuccessfully) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    override suspend fun getChangeListVersion(): ChangeListVersion =
        preferences.getChangeListVersions()

    override suspend fun updateChangeListVersion(
        version: ChangeListVersion.() -> ChangeListVersion
    ) = preferences.updateChangeListVersion(version)


    companion object {
        /**
         * Expedited one time work to sync data on app startup
         */
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }
}