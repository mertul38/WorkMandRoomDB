package com.example.workmanagerandroomdb

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.lifecycle.LiveData
import androidx.work.*
import com.example.workmanagerandroomdb.RoomURI.MyURI
import com.example.workmanagerandroomdb.RoomURI.MyViewModel
import com.example.workmanagerandroomdb.RoomURI.MyViewModelFactory
import com.example.workmanagerandroomdb.StableRoom.StableURI
import com.example.workmanagerandroomdb.StableRoom.StableViewModel
import com.example.workmanagerandroomdb.StableRoom.StableViewModelFactory
import com.example.workmanagerandroomdb.ui.theme.WorkManagerAndRoomDBTheme
import java.lang.IllegalArgumentException
import java.net.URI
import java.time.Duration
import java.util.concurrent.TimeUnit
import kotlin.random.Random


val TAG = "Worker"

class MainActivity : ComponentActivity() {

    val workManager = WorkManager.getInstance(this@MainActivity)

    lateinit var VM:MyViewModel
    lateinit var stable_VM:StableViewModel
    lateinit var allURIs: List<MyURI>
    lateinit var stable_allURIs: List<StableURI>
    lateinit var workInfos: List<WorkInfo>

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
        stable_VM = StableViewModelFactory(this).create(StableViewModel::class.java)

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted: Boolean ->
                if(isGranted){
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
                                    stable_allURIs = stable_VM.allURIs.observeAsState(listOf()).value

                                    Row{
                                        Column(modifier = Modifier.weight(1f))
                                        {
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
                                            Button(onClick = {
                                                VM.insertURI(MyURI(Uri.parse("mert${Random.nextInt(0,1000)}")))
                                            }){
                                                Text("Add Data", color = Color.White)
                                            }
                                            Spacer(modifier = Modifier.size(5.dp))
                                            Button( onClick = {
                                                VM.deleteLastURI()
                                            }){
                                                Text("Delete Data",color = Color.Red)
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(5.dp))
                                        Column(modifier = Modifier.weight(1f))
                                        {
                                            LazyColumn(modifier = Modifier
                                                .fillMaxWidth()
                                                .height(300.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center){
                                                items (items = stable_allURIs){
                                                    Card(modifier = Modifier
                                                        .background(color = Color.Red)
                                                        .height(50.dp)
                                                        .fillMaxWidth()
                                                    ){
                                                        Text(modifier = Modifier.weight(5f),text = it.uri, color = Color.Green)
                                                    }
                                                    Spacer(modifier = Modifier
                                                        .height(3.dp)
                                                        .background(color = Color.Yellow))
                                                }
                                            }
                                            Button(onClick = {
                                                stable_VM.insertURI(StableURI(Uri.parse("mert${Random.nextInt(0,1000)}")))
                                            }){
                                                Text("Add Data", color = Color.White)
                                            }
                                            Spacer(modifier = Modifier.size(5.dp))
                                            Button( onClick = {
                                                stable_VM.deleteLastURI()
                                            }){
                                                Text("Delete Data",color = Color.Red)
                                            }
                                        }
                                    }
                                    Button(onClick = {
                                        VM.insertURI(MyURI(Uri.parse("mert1001")))
                                        stable_VM.insertURI(StableURI(Uri.parse("mert1001")))
                                    }){
                                        Text("Add Common Data", color = Color.White)
                                    }
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Button(
                                        modifier = Modifier.background(color = Color.Blue),
                                        onClick = {
                                            Log.d("Worker","Clicked: ${Thread.currentThread()}")
                                            SchedulerWorkManager(this@MainActivity).schedule()
                                        })
                                    {
                                        Text("Do Work")
                                    }
                                    Spacer(modifier = Modifier.size(10.dp))
                                    Button(onClick = {
                                        workManager.cancelAllWork()
                                    })
                                    {
                                        Text("Cancel Work")
                                    }
                                    Spacer(modifier = Modifier.size(10.dp))
                                    workInfos = workManager.getWorkInfosByTagLiveData("worker").observeAsState(
                                        listOf()).value
                                    Text("# of works: ${(workInfos as List<WorkInfo>?)?.size}")
                                    Spacer(modifier = Modifier.size(10.dp))

                                }
                            }
                        }
                    }
                }
                else{
                    Log.d(TAG,"olmadi...")
                }
            }
        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}


