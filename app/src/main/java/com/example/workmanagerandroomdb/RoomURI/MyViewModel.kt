package com.example.workmanagerandroomdb.RoomURI

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MyViewModel(context: Context):ViewModel() {

    var allURIs: LiveData<List<MyURI>>
    private val repository: MyURIRepositoryImpl

    init{
        val myURIDao = URIRoomDatabase.getInstance(context).myURIDao()
        repository = MyURIRepositoryImpl(myURIDao)
        allURIs = repository.getAllURIs()
    }

    fun insertURI(newURI: MyURI){
        repository.insertURI(newURI)
    }

    fun deleteLastURI(){
        repository.deleteLastURI()
    }

    fun getSnapshotAllURIs():List<MyURI>{
        return repository.getAllURIs_asSnapshot()
    }

    fun deleteURI(uri: String){
        repository.deleteURI(uri)
    }

    fun updateURI(uri: String){
        if(repository.isExist(uri))
        {
            deleteURI(uri)
        }
        else
        {
            repository.insertURI(MyURI(Uri.parse(uri)))
        }
    }
}