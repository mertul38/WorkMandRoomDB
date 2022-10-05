package com.example.workmanagerandroomdb.RoomURI

import androidx.lifecycle.LiveData

interface MyURIRepository {

    fun getAllURIs(): LiveData<List<MyURI>>
    fun getAllURIs_asSnapshot(): List<MyURI>
    fun insertURI(newURI: MyURI)
    fun deleteLastURI()
    fun isExist(uri: String):Boolean
    fun deleteURI(uri: String)
}