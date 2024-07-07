package com.groot.facerecognition.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class FaceInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
) {


}
