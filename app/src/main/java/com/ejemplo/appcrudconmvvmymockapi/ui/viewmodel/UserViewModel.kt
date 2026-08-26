package com.ejemplo.appcrudconmvvmymockapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ejemplo.appcrudconmvvmymockapi.data.api.RetrofitClient
import com.ejemplo.appcrudconmvvmymockapi.data.model.User
import com.ejemplo.appcrudconmvvmymockapi.data.repository.UserRepository
import com.ejemplo.appcrudconmvvmymockapi.data.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UserViewModel : ViewModel() {
    private val repository = UserRepository(RetrofitClient.apiService)

    private val _usersState = MutableStateFlow<Resource<List<User>>>(Resource.Loading())
    val usersState: StateFlow<Resource<List<User>>> = _usersState

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _usersState.value = Resource.Loading()
            try {
                val response = repository.getUsers()
                _usersState.value = Resource.Success(response)
            } catch (e: IOException) {
                _usersState.value = Resource.Error("No se pudo conectar al servidor. Revisa tu conexión.")
            } catch (e: HttpException) {
                _usersState.value = Resource.Error("Error del servidor: ${e.code()}")
            } catch (e: Exception) {
                _usersState.value = Resource.Error("Ocurrió un error inesperado: ${e.localizedMessage}")
            }
        }
    }

    fun retry() {
        fetchUsers()
    }

    fun addUser(name: String, email: String) {
        viewModelScope.launch {
            try {
                repository.createUser(User(name = name, email = email))
                fetchUsers()
            } catch (e: Exception) {
                _usersState.value = Resource.Error("Error al agregar usuario: ${e.message}")
            }
        }
    }

    fun updateUser(id: String, name: String, email: String) {
        viewModelScope.launch {
            try {
                repository.updateUser(id, User(id = id, name = name, email = email))
                fetchUsers()
            } catch (e: Exception) {
                _usersState.value = Resource.Error("Error al actualizar usuario: ${e.message}")
            }
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteUser(id)
                fetchUsers()
            } catch (e: Exception) {
                _usersState.value = Resource.Error("Error al eliminar usuario: ${e.message}")
            }
        }
    }
}
