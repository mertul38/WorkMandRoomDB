package com.example.workmanagerandroomdb.RoomURI

import android.app.Application
import android.content.Context
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

    fun refreshAllURIs(){
        allURIs = repository.getAllURIs()
    }
}