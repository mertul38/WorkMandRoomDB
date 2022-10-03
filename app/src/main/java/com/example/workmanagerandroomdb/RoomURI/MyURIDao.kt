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



}