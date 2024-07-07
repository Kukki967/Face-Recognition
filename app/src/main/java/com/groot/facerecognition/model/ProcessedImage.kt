package com.groot.facerecognition.model

import androidx.annotation.Keep

@Keep
data class ProcessedImage(
    var id: Int? = null,
    var name: String = ""
) {

}
