package com.example.workmanagerandroomdb

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.*
import java.util.concurrent.TimeUnit

class SchedulerWorkManager(val context: Context) {

    fun schedule(){
        schedulerPeriodicTimeRequest(context)
        schedulerOneTimeRequest(context)
    }

    fun schedulerOneTimeRequest(context: Context){
        Log.d(TAG,"new one time request scheduled...")
        val workManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder()
            .addContentUriTrigger(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,true)
            .setTriggerContentMaxDelay(3000L, TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniqueWork(
            "onetime",
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            OneTimeWorkRequestBuilder<OneTimeWorker>()
                .setConstraints(constraints)
                .addTag("worker")
                .build()
        )
    }

    fun schedulerPeriodicTimeRequest(context: Context) {
        Log.d(TAG,"new periodic time request scheduled!!!")
        val workManager = WorkManager.getInstance(context)
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            PeriodicTimeWorker::class.java,
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MILLISECONDS)
            .addTag("worker")
            .build()

        workManager.enqueueUniquePeriodicWork(
            "periodic",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest).state

    }
}