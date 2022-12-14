package com.example.workmanagerandroomdb.RoomURI

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.workmanagerandroomdb.RoomURI.MyURI

@Dao
interface MyURIDao {

    @Insert
    fun insertURI(uri: MyURI)

    @Query("DELETE FROM URIs WHERE Id = (SELECT MAX(Id) FROM URIs)")
    fun deleteLastURI()

    @Query("SELECT * FROM URIs")
    fun getAllURIs(): LiveData<List<MyURI>>

    @Query("SELECT * FROM URIs")
    fun getAllUris_asSnapshot(): List<MyURI>

    @Query("SELECT EXISTS(SELECT * FROM URIs WHERE Uri = :uri)")
    fun isExist(uri: String): Boolean

    @Query("DELETE FROM URIS WHERE Uri = :uri")
    fun deleteURI(uri: String)


}