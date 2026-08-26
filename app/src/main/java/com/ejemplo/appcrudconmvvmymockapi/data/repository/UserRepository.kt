package com.ejemplo.appcrudconmvvmymockapi.data.repository

import com.ejemplo.appcrudconmvvmymockapi.data.api.ApiService
import com.ejemplo.appcrudconmvvmymockapi.data.model.User

class UserRepository(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()
    suspend fun getUser(id: String) = apiService.getUser(id)
    suspend fun createUser(user: User) = apiService.createUser(user)
    suspend fun updateUser(id: String, user: User) = apiService.updateUser(id, user)
    suspend fun deleteUser(id: String) = apiService.deleteUser(id)
}