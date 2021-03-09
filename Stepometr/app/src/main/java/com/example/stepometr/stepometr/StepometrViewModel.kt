package com.example.stepometr.stepometr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StepometrViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Stepometr"
    }
    val text: LiveData<String> = _text
}