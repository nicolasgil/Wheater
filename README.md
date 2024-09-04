# **WeatherApp**

## Descripción:
WeatherApp es una aplicación Android desarrollada en Kotlin con Jetpack Compose, que sigue los principios de Clean Architecture y utiliza la arquitectura MVVM, Retrofit, SharedPreferences y Coroutines para proporcionar una experiencia eficiente y moderna. La aplicación consume una API de clima para mostrar el pronóstico actual y de los próximos días de varias ubicaciones alrededor del mundo. Permite realizar búsquedas dinámicas de ciudades, mostrando detalles detallados del clima, incluidos iconos visuales representando el estado del tiempo, como soleado, lluvioso o nublado.

## Características Principales:
* **Clean Architecture**: La aplicación sigue los principios de Clean Architecture, lo que facilita la modularidad, el mantenimiento y la escalabilidad del código.
* **MVVM**: Utiliza el patrón de diseño MVVM (Model-View-ViewModel) para separar la lógica de presentación y el manejo de datos.
* **Compose**: La interfaz de usuario se construye utilizando Jetpack Compose, ofreciendo una forma declarativa y moderna de diseñar interfaces de usuario en Android.
* **Retrofit**: Se utiliza Retrofit para realizar solicitudes HTTP de manera sencilla y eficiente.
* **SharedPreferences**: La persistencia de datos se gestiona mediante SharedPreferences, facilitando el almacenamiento de configuraciones y datos temporales.
* **Coroutines**: Se aprovechan las coroutines de Kotlin para realizar operaciones asíncronas de manera concisa y eficiente.
* **Pruebas Unitarias**: Se implementan pruebas unitarias utilizando JUnit junto con Mock y Mockito para verificar el comportamiento correcto de unidades individuales de código de manera aislada.

## Requisitos del Sistema
Dispositivo con sistema operativo Android 5.0 (API nivel 21 | Android Lollipop) o superior.

## Instalación
1. Clona el repositorio: `git clone https://github.com/nicolasgil/weather.git`
2. Abre el proyecto en Android Studio.
3. Ejecuta la aplicación en un dispositivo o emulador Android.

## IMPORTANTE
Tener en cuenta que la versión de Gradle utilizada en la construcción de la aplicación fue la 8.2 y la versión de Android Studio es Hedgehog | 2023.1.1 Patch 2.

---

##### Hecho por Nicolas Gil Villa https://github.com/nicolasgil | https://www.linkedin.com/in/nicolasgilvilla/
