package com.example.workmanagerandroomdb.StableRoom

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.net.URI

@Entity(tableName = "URIs")
class StableURI(realUri: Uri? = null) {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    var id: Int = 0

    @ColumnInfo(name = "Uri")
    var uri: String = ""

    init {
        this.uri = realUri.toString()
    }

}