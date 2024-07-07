package com.groot.facerecognition.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.groot.facerecognition.data.dao.FaceInfoDao
import com.groot.facerecognition.model.FaceInfo
@Database(entities = [FaceInfo::class], version = 1)
@TypeConverters(BaseConvertor::class)
abstract class MainDatabase : RoomDatabase() {
    abstract val dao: FaceInfoDao
}

