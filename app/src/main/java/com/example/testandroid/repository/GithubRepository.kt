package com.example.testandroid.repository

import com.example.testandroid.api.CallApi
import com.example.testandroid.model.User
import javax.inject.Inject

class GithubRepository @Inject constructor(private val githubApi: CallApi) {
    suspend fun getListUser(since: Int, perPage:Int) : List<User> {
        return githubApi.fetchUserList(since,perPage)
    }
    suspend fun getUser(loginId:String): User{
        return githubApi.fetchUserProfile(loginId)
    }
}