package com.example.nhatthanh.di

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideResource(context: Context): Resources = context.resources

    @Singleton
    @Provides
    fun provideAssetManger(context: Context): AssetManager = context.assets
}