package com.example.workmanagerandroomdb.StableRoom

import androidx.lifecycle.LiveData

interface StableURIRepository {

    fun getAllURIs(): LiveData<List<StableURI>>
    fun getAllURIs_asSnapshot(): List<StableURI>
    fun insertURI(newURI: StableURI)
    fun deleteLastURI()
    fun isExist(uri: String):Boolean
    fun deleteURI(uri: String)
}