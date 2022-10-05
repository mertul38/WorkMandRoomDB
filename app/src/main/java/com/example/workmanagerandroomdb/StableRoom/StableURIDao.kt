package com.example.workmanagerandroomdb.StableRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.workmanagerandroomdb.RoomURI.MyURI

@Dao
interface StableURIDao {

    @Insert
    fun insertURI(uri: StableURI)

    @Query("DELETE FROM URIs WHERE Id = (SELECT MAX(Id) FROM URIs)")
    fun deleteLastURI()

    @Query("SELECT * FROM URIs")
    fun getAllURIs(): LiveData<List<StableURI>>

    @Query("SELECT * FROM URIs")
    fun getAllUris_asSnapshot(): List<StableURI>

    @Query("SELECT EXISTS(SELECT * FROM URIs WHERE Uri = :uri)")
    fun isExist(uri: String): Boolean

    @Query("DELETE FROM URIS WHERE Uri = :uri")
    fun deleteURI(uri: String)


}