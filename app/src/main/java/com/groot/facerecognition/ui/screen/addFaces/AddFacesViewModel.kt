package com.groot.facerecognition.ui.screen.addFaces

import androidx.lifecycle.ViewModel
import com.groot.facerecognition.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFaceViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

}