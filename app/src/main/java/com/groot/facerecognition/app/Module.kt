package com.groot.facerecognition.app

import android.app.Application
import androidx.room.Room
import com.groot.facerecognition.data.Repository
import com.groot.facerecognition.data.database.BaseConvertor
import com.groot.facerecognition.data.database.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideListConverter(): BaseConvertor = BaseConvertor()

    @Provides
    @Singleton
    fun provideDatabase(app: Application, baseConvertor: BaseConvertor): MainDatabase =
        Room.databaseBuilder(app, MainDatabase::class.java, "MainDatabase").addTypeConverter(baseConvertor).build()

    @Provides
    @Singleton
    fun provideFaceRepo(app: Application, db: MainDatabase): Repository = Repository(app, db)

}