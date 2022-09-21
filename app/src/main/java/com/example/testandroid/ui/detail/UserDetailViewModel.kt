package com.example.testandroid.ui.detail

import androidx.compose.runtime.MutableState
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
class UserDetailViewModel @Inject constructor(private val githubRepository: GithubRepository) :ViewModel(){
    private val _state = MutableStateFlow(emptyList<User>())
    val state : StateFlow<List<User>> get() = _state

    fun getUser(loginId: String){
        viewModelScope.launch {
           val user= githubRepository.getUser(loginId)
            _state.value = listOf(user)
        }
    }
}