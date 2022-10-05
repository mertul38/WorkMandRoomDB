package com.example.workmanagerandroomdb.StableRoom

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StableViewModelFactory(val context: Context):
ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StableViewModel(context) as T
    }

}