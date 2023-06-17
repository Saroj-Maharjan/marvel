package com.sawrose.marvelapp.sync

import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.sawrose.marvelapp.sync.workers.SyncWorker
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class SyncWorkerTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    private val context get() = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun setUp() {
        val config = Configuration
            .Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }


    @Test
    fun testSyncWorker() {
        val request = SyncWorker.startUpSyncWork()

        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)!!

        // Enqueue and wait for result.
        workManager.enqueue(request).result.get()

        // get workInfo and outputData
        val preRunWorkInfo = workManager.getWorkInfoById(request.id).get()

        //Assert
        assertEquals(WorkInfo.State.ENQUEUED, preRunWorkInfo.state)

        testDriver.setAllConstraintsMet(request.id)

        val postRequirementInfo = workManager.getWorkInfoById(request.id).get()
        assertEquals(WorkInfo.State.RUNNING, postRequirementInfo.state)
    }
}