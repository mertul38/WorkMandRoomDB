package com.example.workmanagerandroomdb

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerandroomdb.RoomURI.MyURI
import com.example.workmanagerandroomdb.RoomURI.MyViewModel
import com.example.workmanagerandroomdb.RoomURI.MyViewModelFactory
import kotlinx.coroutines.delay

val TAG = "Worker"
class MyWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context,workerParams) {

    val VM  = MyViewModelFactory(context).create(MyViewModel::class.java)
    val outContext = context
    override suspend fun doWork(): Result {
        scheduler(outContext)
        Log.d(TAG,"***\nstart doWork: ${Thread.currentThread()}\ntriggered content size: ${this.triggeredContentUris.size}")
        for(uri in this.triggeredContentUris){
            Log.d(TAG,"uri: ${uri.encodedPath}")
            VM.insertURI(MyURI(Uri.parse("tugba-${uri.toString().substring(0,5)}")))
        }
        Log.d(TAG,"***")
        for(sec in 0..5){
            Log.d(TAG,"sec: $sec")
            delay(1000L)
        }
        Log.d(TAG,"finish doWork...")
        return Result.success()
    }
}