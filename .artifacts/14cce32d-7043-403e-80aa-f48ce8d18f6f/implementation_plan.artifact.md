# Plan de Corrección de Errores y Sincronización de Paquetes

El proyecto presenta múltiples errores de compilación debido a que el código fue importado o copiado de otro proyecto (`com.example.p3`) y mantiene referencias a ese paquete, además de tener errores de sintaxis como declaraciones de paquetes duplicadas e imports faltantes.

## User Review Required

> [!IMPORTANT]
> Se realizarán cambios en casi todos los archivos del proyecto para corregir los nombres de los paquetes e imports. Esto es esencial para que el proyecto compile.
> Además, se crearán las pantallas de "User" que faltan para que la navegación en `MainActivity` sea funcional.

## Proposed Changes

### 1. Corrección de Modelos y Datos
Se corregirán los paquetes, se eliminarán duplicados y se añadirán los imports de GSON.

#### [MODIFY] [Item.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/data/model/Item.kt)
- Añadir import `com.google.gson.annotations.SerializedName`.
#### [MODIFY] [User.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/data/model/User.kt)
- Añadir import `com.google.gson.annotations.SerializedName`.

### 2. Corrección de API y Repositorios
Limpieza de paquetes duplicados y corrección de imports de modelos.

#### [MODIFY] [ApiService.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/data/api/ApiService.kt)
#### [MODIFY] [RetrofitClient.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/data/api/RetrofitClient.kt)
#### [MODIFY] [ItemRepository.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/data/repository/ItemRepository.kt)
#### [MODIFY] [UserRepository.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/data/repository/UserRepository.kt)

### 3. Corrección de UI (ViewModels y Screens)
Sincronización de paquetes e implementación de pantallas faltantes.

#### [MODIFY] [ItemViewModel.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/ui/viewmodel/ItemViewModel.kt)
#### [MODIFY] [UserViewModel.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/ui/viewmodel/UserViewModel.kt)
#### [MODIFY] [ItemListScreen.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/ui/screens/ItemListScreen.kt)
#### [MODIFY] [ItemUpsertScreen.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/ui/screens/ItemUpsertScreen.kt)
#### [NEW] [UserListScreen.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/ui/screens/UserListScreen.kt)
- Implementación de la lista de usuarios.
#### [NEW] [UserUpsertScreen.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/ui/screens/UserUpsertScreen.kt)
- Formulario para crear/editar usuarios.

### 4. MainActivity y Temas
Corrección de la navegación y el tema de la aplicación.

#### [MODIFY] [MainActivity.kt](file:///home/juan/AndroidStudioProjects/AppCRUDconMVVMyMockAPI/app/src/main/java/com/ejemplo/appcrudconmvvmymockapi/MainActivity.kt)
- Cambiar imports de `com.example.p3` a `com.ejemplo.appcrudconmvvmymockapi`.
- Usar un tema genérico de Material3 si `P3Theme` no existe.

## Verification Plan

### Automated Tests
- Ejecutar `gradlew assembleDebug` para verificar que todos los errores de compilación hayan desaparecido.

### Manual Verification
- Desplegar la app y verificar que la navegación entre Items y Usuarios funcione.
- Probar las operaciones CRUD (Crear, Listar, Editar, Eliminar) para ambos recursos.
