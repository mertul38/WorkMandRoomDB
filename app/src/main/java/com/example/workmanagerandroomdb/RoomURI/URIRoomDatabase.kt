package com.example.workmanagerandroomdb.RoomURI

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MyURI::class), version = 2)
abstract class URIRoomDatabase: RoomDatabase() {

    abstract fun myURIDao(): MyURIDao

    companion object{

        private var INSTANCE: URIRoomDatabase? = null

        fun getInstance(context: Context): URIRoomDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            synchronized(this){
                /*val instance = Room
                    .inMemoryDatabaseBuilder(
                        context,
                        URIRoomDatabase::class.java
                    ).allowMainThreadQueries()
                    .build()*/
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    URIRoomDatabase::class.java,
                    "videoDataDatabase"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}