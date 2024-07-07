package com.groot.facerecognition.data.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.groot.facerecognition.model.FaceInfo

@Dao
interface FaceInfoDao {
    @Insert
    suspend fun insert(faceInfo: FaceInfo)

    @Update
    suspend fun update(faceInfo: FaceInfo)

    @Delete
    suspend fun delete(faceInfo: FaceInfo)

    @Query("SELECT * FROM face_info WHERE id = :faceInfoId")
    suspend fun getFaceInfoById(faceInfoId: Int): FaceInfo

    @Query("SELECT * FROM face_info")
    suspend fun getAllFaceInfo(): List<FaceInfo>

}