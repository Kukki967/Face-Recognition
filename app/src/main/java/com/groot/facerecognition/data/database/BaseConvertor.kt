package com.groot.facerecognition.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.groot.facerecognition.model.FaceInfo

@ProvidedTypeConverter
class BaseConvertor {
    @TypeConverter
    fun fromString(value: String): FaceInfo {
        val parts = value.split("|")
        return FaceInfo(
            id = parts[0].toIntOrNull(),
            name = parts[1]
        )
    }

    @TypeConverter
    fun toString(faceInfo: FaceInfo): String {
        return "${faceInfo.id ?: ""}|${faceInfo.name}"
    }
}