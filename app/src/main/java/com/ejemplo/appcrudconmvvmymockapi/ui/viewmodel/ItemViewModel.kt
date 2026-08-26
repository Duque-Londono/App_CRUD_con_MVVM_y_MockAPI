package com.ejemplo.appcrudconmvvmymockapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ejemplo.appcrudconmvvmymockapi.data.api.RetrofitClient
import com.ejemplo.appcrudconmvvmymockapi.data.model.Item
import com.ejemplo.appcrudconmvvmymockapi.data.repository.ItemRepository
import com.ejemplo.appcrudconmvvmymockapi.data.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ItemViewModel : ViewModel() {
    private val repository = ItemRepository(RetrofitClient.apiService)

    private val _itemsState = MutableStateFlow<Resource<List<Item>>>(Resource.Loading())
    val itemsState: StateFlow<Resource<List<Item>>> = _itemsState

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            _itemsState.value = Resource.Loading()
            try {
                val response = repository.getItems()
                _itemsState.value = Resource.Success(response)
            } catch (e: IOException) {
                _itemsState.value = Resource.Error("No se pudo conectar al servidor. Revisa tu conexión.")
            } catch (e: HttpException) {
                _itemsState.value = Resource.Error("Error del servidor: ${e.code()}")
            } catch (e: Exception) {
                _itemsState.value = Resource.Error("Ocurrió un error inesperado: ${e.localizedMessage}")
            }
        }
    }

    fun retry() {
        fetchItems()
    }

    fun addItem(name: String, description: String) {
        viewModelScope.launch {
            try {
                repository.createItem(Item(name = name, description = description))
                fetchItems()
            } catch (e: Exception) {
                // Manejo de error para acciones específicas puede ser más detallado (ej. Toast o Snackbar)
                _itemsState.value = Resource.Error("Error al agregar item: ${e.message}")
            }
        }
    }

    fun updateItem(id: String, name: String, description: String) {
        viewModelScope.launch {
            try {
                repository.updateItem(id, Item(id = id, name = name, description = description))
                fetchItems()
            } catch (e: Exception) {
                _itemsState.value = Resource.Error("Error al actualizar item: ${e.message}")
            }
        }
    }

    fun deleteItem(id: String) {
        viewModelScope.launch {
            try {
                repository.deleteItem(id)
                fetchItems()
            } catch (e: Exception) {
                _itemsState.value = Resource.Error("Error al eliminar item: ${e.message}")
            }
        }
    }
}
