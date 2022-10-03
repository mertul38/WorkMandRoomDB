package com.example.workmanagerandroomdb.RoomURI

import androidx.lifecycle.LiveData

interface MyURIRepository {

    fun getAllURIs(): LiveData<List<MyURI>>
    fun insertURI(newURI: MyURI)
    fun deleteLastURI()
}