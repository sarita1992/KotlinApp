
# Android Kotlin App

This is an Android application developed using Kotlin. The application is built with Android Studio Koala | 2024.1.1 and uses Gradle for build and dependency management.

## Setup

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Run the application on an emulator or physical device.

## Dependencies

The project uses the following dependencies:

- AndroidX Core KTX
- AndroidX AppCompat
- Material Design
- AndroidX Material3
- AndroidX UI Tooling Preview
- AndroidX Activity Compose
- Kotlin Coroutines
- Koin for dependency injection
- WorkManager for background tasks
- Coil for image loading
- AndroidX LiveData
- Gson for JSON processing
- Retrofit for network requests
- OkHttp Logging Interceptor
- AndroidX Hilt Work
- AndroidX Benchmark
- AndroidX Lifecycle Runtime Compose
- AndroidX Security Crypto



##Application use cases:
- Here I have used compose for UI design.
- Retrofit used for api calling.
- Flow use to handle the api failure retry case.
- For caching the books data I have used SharedPreference with encryption using the AndroidX Security Crypto.
- When there is failure in api the data will be shown from the cache value.
- List of book is sorted from latest to old released date order.



## Authors

- [@sarita](https://github.com/sarita1992/AndroidKotlinApplication)

