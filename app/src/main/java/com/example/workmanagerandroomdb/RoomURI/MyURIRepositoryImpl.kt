package com.example.workmanagerandroomdb.RoomURI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MyURIRepositoryImpl(private val myURIDao: MyURIDao):MyURIRepository {

    override fun getAllURIs(): LiveData<List<MyURI>> {
        return myURIDao.getAllURIs()
    }

    override fun getAllURIs_asSnapshot(): List<MyURI> {
        return myURIDao.getAllUris_asSnapshot()
    }

    override fun insertURI(newURI: MyURI) {
        myURIDao.insertURI(newURI)
    }

    override fun deleteLastURI() {
        myURIDao.deleteLastURI()
    }

    override fun isExist(uri: String): Boolean {
        return myURIDao.isExist(uri)
    }

    override fun deleteURI(uri: String) {
        myURIDao.deleteURI(uri)
    }
}