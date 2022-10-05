package com.example.workmanagerandroomdb.StableRoom

import androidx.lifecycle.LiveData

class StableURIRepositoryImpl(private val stableURIDao: StableURIDao):StableURIRepository {

    override fun getAllURIs(): LiveData<List<StableURI>> {
        return stableURIDao.getAllURIs()
    }

    override fun getAllURIs_asSnapshot(): List<StableURI> {
        return stableURIDao.getAllUris_asSnapshot()
    }

    override fun insertURI(newURI: StableURI) {
        stableURIDao.insertURI(newURI)
    }

    override fun deleteLastURI() {
        stableURIDao.deleteLastURI()
    }

    override fun isExist(uri: String): Boolean {
        return stableURIDao.isExist(uri)
    }

    override fun deleteURI(uri: String) {
        stableURIDao.deleteURI(uri)
    }
}