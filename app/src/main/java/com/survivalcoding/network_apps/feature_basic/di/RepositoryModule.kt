package com.survivalcoding.network_apps.feature_basic.di

import com.survivalcoding.network_apps.feature_basic.data.conference.datasource.ConferenceDataSource
import com.survivalcoding.network_apps.feature_basic.data.conference.repository.ConferenceRepositoryImpl
import com.survivalcoding.network_apps.feature_basic.data.todo.TodoDataSourceModule
import com.survivalcoding.network_apps.feature_basic.data.todo.datasource.TodoDataSource
import com.survivalcoding.network_apps.feature_basic.data.todo.repository.TodoRepositoryImpl
import com.survivalcoding.network_apps.feature_basic.domain.repository.ConferenceRepository
import com.survivalcoding.network_apps.feature_basic.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideTodoRepository(@TodoDataSourceModule.TodoRemoteDataSourceQualifier dataSource: TodoDataSource): TodoRepository = TodoRepositoryImpl(dataSource)

    @Provides
    @ViewModelScoped
    fun provideConferenceRepository(dataSource: ConferenceDataSource): ConferenceRepository = ConferenceRepositoryImpl(dataSource)
}