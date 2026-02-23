# Mock GitHub User Explorer

An Android application that searches for GitHub users and provides detailed statistics about their public repositories. This project was developed as a technical assignment for a Senior Android Developer position, focusing on **Clean Architecture**, **Modularization**, and **Scalable State Management**.

---

## Architecture

The project follows the **Clean Architecture** pattern to ensure separation of concerns and testability:

* **Feature Layer**: Built with **Jetpack Compose** and **MVVM**. It observes UI states from the ViewModel using Kotlin Flows.
* **Domain Layer**: Contains the core business logic, including UseCases for user searching and repository data aggregation.
* **Data Layer**: Responsible for data fetching via **Retrofit**. It implements the Repository pattern to abstract the data sources from the domain layer.



---

## Key Features

* **User Search**: Retrieve GitHub user profile information including avatar and name.
* **Repository List**: Display a scrollable list of public repositories with descriptions and statistics.
* **Total Forks Aggregation**: A senior-level logic implementation that calculates the sum of forks across all repositories.
* **Star Badge System**: A conditional UI logic that highlights users with more than 5,000 total forks with a distinct visual badge.
* **Edge-to-Edge UI**: Implementation of modern Android display standards for a more immersive experience.

---

## Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Kotlin (1.9.22) |
| **UI Framework** | Jetpack Compose (Material 3) |
| **Dependency Injection** | Hilt |
| **Networking** | Retrofit 2 & OkHttp 4 |
| **Serialization** | Kotlinx Serialization |
| **Image Loading** | Coil |
| **Navigation** | Jetpack Navigation Compose |
| **Asynchronous** | Kotlin Coroutines & Flow |

---

## Build & Setup

1.  Clone the repository.
2.  Ensure you have Android Studio Jellyfish or higher.
3.  Sync the project with Gradle files (uses **Version Catalog** for dependency management).
4.  Run the `app` module on an emulator or physical device (API 26+).

---

## Unit Testing

Business logic within the Domain Layer is verified through **JUnit 4** and **MockK**.
* `GetTotalForksUseCaseTest`: Verifies the accuracy of the fork summation logic.
* `GetUserUseCaseTest`: Validates user profile data retrieval.
* `GetReposUseCaseTest`: Ensures repository list handling and empty state scenarios.

---

## Performance Optimization

* **R8/Proguard**: Configured to minimize bundle size and obfuscate code.
* **KSP**: Utilized for faster annotation processing compared to Kapt.
* **Composition Best Practices**: Minimal recompositions achieved by using stable data models and efficient state handling.
