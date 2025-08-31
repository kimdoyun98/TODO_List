package com.project.data.di

import android.content.Context
import com.project.data.local.preference.PreferenceUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Singleton
    @Provides
    fun preference(
        @ApplicationContext context: Context
    ): PreferenceUtil = PreferenceUtil(context)
}
