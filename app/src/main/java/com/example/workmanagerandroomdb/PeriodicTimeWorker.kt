package com.example.workmanagerandroomdb

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerandroomdb.RoomURI.MyURI
import com.example.workmanagerandroomdb.RoomURI.MyViewModel
import com.example.workmanagerandroomdb.RoomURI.MyViewModelFactory
import com.example.workmanagerandroomdb.StableRoom.StableURI
import com.example.workmanagerandroomdb.StableRoom.StableViewModel
import com.example.workmanagerandroomdb.StableRoom.StableViewModelFactory
import java.io.File

class PeriodicTimeWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context,workerParams) {
    val VM  = MyViewModelFactory(context).create(MyViewModel::class.java)
    val stable_VM = StableViewModelFactory(context).create(StableViewModel::class.java)
    val outContext = context
    val file = File(outContext.getExternalFilesDir(null).toString()+"/mert.txt")
    var allURIs: List<MyURI>? = null

    override suspend fun doWork(): Result {
        Log.d(TAG,"periodic started...")
        allURIs = VM.getSnapshotAllURIs()

        Log.d(TAG, "after scheduling: ${VM.allURIs.value?.size}")
        if(allURIs != null)
        {
            for (i in (0 until allURIs!!.size)){
                val temp = allURIs!!.get(i)
                Log.d(TAG,"writing ${temp.id}")
                stable_VM.updateURI(temp.uri)
                VM.deleteURI(temp.uri)
            }
        }
        SchedulerWorkManager(context = outContext).schedulerPeriodicTimeRequest(outContext)
        return Result.success()
    }


}