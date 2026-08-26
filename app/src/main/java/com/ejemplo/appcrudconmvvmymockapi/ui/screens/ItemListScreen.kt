package com.ejemplo.appcrudconmvvmymockapi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ejemplo.appcrudconmvvmymockapi.data.util.Resource
import com.ejemplo.appcrudconmvvmymockapi.ui.viewmodel.ItemViewModel

@Composable
fun ItemListScreen(
    viewModel: ItemViewModel,
    onEditItem: (String) -> Unit,
    onAddItem: () -> Unit
) {
    val state by viewModel.itemsState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddItem) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (state) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Error -> {
                    ErrorView(
                        message = state.message ?: "Error desconocido",
                        onRetry = { viewModel.retry() },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is Resource.Success -> {
                    val items = state.data ?: emptyList()
                    if (items.isEmpty()) {
                        Text(
                            text = "No hay datos disponibles",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn {
                            items(items) { item ->
                                ListItem(
                                    leadingContent = {
                                        AsyncImage(
                                            model = item.avatar,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                    },
                                    headlineContent = { Text(item.name) },
                                    supportingContent = { Text(item.description) },
                                    trailingContent = {
                                        Row {
                                            IconButton(onClick = { onEditItem(item.id!!) }) {
                                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                                            }
                                            IconButton(onClick = { viewModel.deleteItem(item.id!!) }) {
                                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                                            }
                                        }
                                    }
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Reintentar")
            }
        }
    }
}
