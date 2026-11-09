package com.nikitadevktad.vkeducation.ui.greeting

import androidx.lifecycle.ViewModel
import com.nikitadevktad.vkeducation.data.repository.GreetingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val repository: GreetingRepository,
) : ViewModel() {
    val name: String = repository.getName()
}
