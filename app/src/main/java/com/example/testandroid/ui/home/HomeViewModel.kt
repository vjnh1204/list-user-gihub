package com.example.testandroid.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.model.User
import com.example.testandroid.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val githubRepository: GithubRepository):ViewModel() {
    private val _state = MutableStateFlow(emptyList<User>())
    val state: StateFlow<List<User>> get() = _state

    init {
        viewModelScope.launch {
            val users = githubRepository.getListUser(1,100)
            _state.value = users
        }
    }
}