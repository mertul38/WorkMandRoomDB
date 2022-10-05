package com.example.workmanagerandroomdb.StableRoom

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class StableViewModel(context: Context):ViewModel() {

    var allURIs: LiveData<List<StableURI>>
    private val repository: StableURIRepositoryImpl

    init{
        val myURIDao = StableURIRoomDatabase.getInstance(context).stableURIDao()
        repository = StableURIRepositoryImpl(myURIDao)
        allURIs = repository.getAllURIs()
    }

    fun insertURI(newURI: StableURI){
        repository.insertURI(newURI)
    }

    fun deleteLastURI(){
        repository.deleteLastURI()
    }

    fun getSnapshotAllURIs():List<StableURI>{
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
            repository.insertURI(StableURI(Uri.parse(uri)))
        }
    }
}