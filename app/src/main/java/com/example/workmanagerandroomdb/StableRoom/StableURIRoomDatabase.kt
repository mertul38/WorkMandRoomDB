package com.example.workmanagerandroomdb.StableRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(StableURI::class), version = 2)
abstract class StableURIRoomDatabase: RoomDatabase() {

    abstract fun stableURIDao(): StableURIDao

    companion object{

        private var INSTANCE: StableURIRoomDatabase? = null

        fun getInstance(context: Context): StableURIRoomDatabase {
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
                    StableURIRoomDatabase::class.java,
                    "stableDataDatabase"
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