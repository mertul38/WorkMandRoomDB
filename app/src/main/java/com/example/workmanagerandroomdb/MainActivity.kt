package com.example.workmanagerandroomdb

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.example.workmanagerandroomdb.RoomURI.MyURI
import com.example.workmanagerandroomdb.RoomURI.MyViewModel
import com.example.workmanagerandroomdb.RoomURI.MyViewModelFactory
import com.example.workmanagerandroomdb.ui.theme.WorkManagerAndRoomDBTheme
import java.lang.IllegalArgumentException
import java.net.URI
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    val workManager = WorkManager.getInstance(this@MainActivity)

    lateinit var VM:MyViewModel
    lateinit var allURIs: List<MyURI>

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        VM = MyViewModelFactory(this).create(MyViewModel::class.java)



        setContent {
            WorkManagerAndRoomDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.background(color = Color.Green),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        allURIs = VM.allURIs.observeAsState(listOf()).value
                        LazyColumn(modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                            items (items = allURIs){
                                Card(modifier = Modifier
                                    .background(color = Color.Red)
                                    .height(50.dp)
                                    .fillMaxWidth()
                                ){
                                    Text(modifier = Modifier.weight(5f),text = it.uri, color = Color.White)
                                }
                                Spacer(modifier = Modifier
                                    .height(3.dp)
                                    .background(color = Color.Yellow))
                            }
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Button(onClick = {
                            VM.insertURI(MyURI(Uri.parse("mert")))
                            Log.d(TAG,"size: ${allURIs.size} - ${VM.allURIs.value?.size}")
                        }){
                            Text("Add Data", color = Color.White)
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Button( onClick = {
                            VM.deleteLastURI()
                        }){
                            Text("Delete Data",color = Color.Red)
                        }
                        Spacer(modifier = Modifier.size(5.dp))
                        Button(
                            modifier = Modifier.background(color = Color.Blue),
                            onClick = {
                                Log.d("Worker","Clicked: ${Thread.currentThread()}")
                                scheduler(this@MainActivity)
                            })
                        {
                            Text("Do Work")
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(onClick = {
                            workManager.cancelAllWorkByTag("myworker")
                        })
                        {
                            Text("Cancel Work")
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        var counter by remember{ mutableStateOf(0)}
                        Button(onClick = {
                            counter++
                        })
                        {
                            Text("Number: $counter")
                        }
                    }
                }
            }
        }
    }
}

fun scheduler(context: Context){
    Log.d(TAG,"new request scheduled...")
    val workManager = WorkManager.getInstance(context)
    val constraints = androidx.work.Constraints.Builder()
        .addContentUriTrigger(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,true)
        .setTriggerContentMaxDelay(1600L,TimeUnit.MILLISECONDS)
        .build()
    workManager.enqueueUniqueWork(
        MyWorker::javaClass.name,
        ExistingWorkPolicy.APPEND,
        OneTimeWorkRequestBuilder<MyWorker>().setConstraints(constraints).build()
    )
}
