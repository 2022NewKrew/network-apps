package com.survivalcoding.network_apps.feature_basic.presentation.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.survivalcoding.network_apps.feature_basic.data.datasource.local.TodoLocalDataSource
import com.survivalcoding.network_apps.feature_basic.data.datasource.remote.RetrofitClient
import com.survivalcoding.network_apps.feature_basic.data.datasource.remote.TodoRemoteDataSource
import com.survivalcoding.network_apps.feature_basic.data.repository.TodoRepositoryImpl
import com.survivalcoding.network_apps.feature_basic.presentation.BasicViewModel

class BasicViewModelProvider : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BasicViewModel::class.java)) {
            return BasicViewModel(
                TodoRepositoryImpl(TodoRemoteDataSource(RetrofitClient.apiService))
            ) as T
        }
        return super.create(modelClass)
    }
}