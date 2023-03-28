![logo](https://user-images.githubusercontent.com/93789076/228204408-303cdf05-7fbb-4788-a260-501ca5493b7e.png)
# Weight Tracker

Weight tracker is an app primarily for BMI calculation (supporting different units), goal & ideal weight, weight/activity/water records, health articles, nutrients info, recipes & exercises, converting units, motivation and more.

|          |             |                |       |
| :---:    |    :----:   |          :---: | :---: |
| ![1](https://user-images.githubusercontent.com/93789076/228212209-21883ede-c5de-4538-8ca8-be28e8478093.jpg) | ![2](https://user-images.githubusercontent.com/93789076/228212389-6891422b-7a98-4ff5-9f5d-0913511581f7.jpg) | ![3](https://user-images.githubusercontent.com/93789076/228212479-1b10b449-6389-4640-a836-e8f28da6bbc8.jpg) | ![4](https://user-images.githubusercontent.com/93789076/228212528-4fdd3998-8f3d-4ccc-9966-b54d14c4dde3.jpg)
| ![5](https://user-images.githubusercontent.com/93789076/228212586-d718dd1a-78af-4dfa-92f6-6abb08487120.jpg) | ![6](https://user-images.githubusercontent.com/93789076/228212645-9ced1faa-98f6-4ad8-a729-ac86e8af20a1.jpg) | ![7](https://user-images.githubusercontent.com/93789076/228212685-40cb170d-bc29-46af-92db-c7c358e73164.jpg) | ![8](https://user-images.githubusercontent.com/93789076/228212732-4e22e9ec-db20-42ae-a1a3-e3187dfd0637.jpg) | 
![9](https://user-images.githubusercontent.com/93789076/228212879-2e6d7831-0a15-4cf2-89a1-fc0b5f05901e.jpg) | ![10](https://user-images.githubusercontent.com/93789076/228213030-7c93eb0d-ffce-4836-a8dd-2c7bbb330dd8.jpg) | ![11](https://user-images.githubusercontent.com/93789076/228213295-d27223f2-a86b-4d08-a194-3478328cce97.jpg) | ![12](https://user-images.githubusercontent.com/93789076/228213370-3c720aab-5866-4ad2-83eb-ec7179ef22a7.jpg) | 
![13](https://user-images.githubusercontent.com/93789076/228213509-b541aa98-0ca0-4957-b058-920a4b45345f.jpg) | ![14](https://user-images.githubusercontent.com/93789076/228213645-4248b2cf-31fb-4c25-9cbc-e0690ce0dfe8.jpg) | ![15](https://user-images.githubusercontent.com/93789076/228213748-44f20e6a-3b4f-4589-812c-8496cf9c2350.jpg) | ![16](https://user-images.githubusercontent.com/93789076/228213863-43849794-7309-4807-9bbb-4b96bb834293.jpg)

**Weight Tracker** is a free health & fitness android app written using **100% Jetpack Compose** and **Kotlin**. It's designed to help you track your weight with ease, supporting you trough your weight loss or weight gain journey in a stress-free way while providing the most valuable tools for it.

## Tech Stack

### Architecture
- [MVVM (Model-View-ViewModel)](https://www.techtarget.com/whatis/definition/Model-View-ViewModel#:~:text=Model%2DView%2DViewModel%20(MVVM)%20is%20a%20software%20design,Ken%20Cooper%20and%20John%20Gossman.)
- [Clean Architecture](https://developer.android.com/topic/architecture)

### Core
- 100% [Kotlin](https://kotlinlang.org/)
- 100% [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
- [Hilt](https://dagger.dev/hilt/) (DI)
- [ArrowKt](https://arrow-kt.io/) (Functional Programming)

### Local Persistence
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (key-value storage, Shared Preferences replacement)
- [Room DB](https://developer.android.com/training/data-storage/room) (SQLite ORM)

### Networking
- [Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html) (REST client)
- [Gson](https://github.com/google/gson) (JSON serialization)

### CI/CD
- [Gradle Groovy DSL](https://gradle.org/)
- [Github Actions](https://github.com/Ivy-Apps/ivy-wallet/actions) (CI/CD)
