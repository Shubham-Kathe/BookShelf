# BookShelf App

BookShelf is a modular, modern Android app built using **Clean Architecture**, **MVVM**, and **SOLID principles**. It allows users to browse a list of books and view detailed information about each one. The project is fully modularized and showcases best practices in Android development, including **Jetpack Compose**, **Kotlin Coroutines**, **Flow**, and **Hilt** for dependency injection.

---

##  Architecture Overview

The app follows **Clean Code Architecture** with a strong separation of concerns across layers:

---
## Module Breakdown

### 1. `:app`
- Sets up **Hilt** for dependency injection
- Handles **Navigation**
- Contains the `BookShelfApp` Compose root and `MainActivity`

### 2. `:presentation`
- Contains UI logic, screens, and state management
- Sub-packages:
    - `booklist`: UI & ViewModel for listing books
    - `bookdetails`: UI & ViewModel for book detail screen
    - `common`: Shared UI components and UI state classes

### 3. `:data`
- Handles all data operations (e.g., API calls, mapping, logging)
- Sub-packages:
    - `remote`: Retrofit services and DTOs
    - `repository`: Repository implementations
    - `mapper`: Transforms DTOs into domain models
    - `logger`: Custom logging tools

### 4. `:domain`
- Contains the core business logic
- Sub-packages:
    - `usecase`: Application-specific use cases
    - `repository`: Interfaces for abstraction
    - `model`: Domain entities
    - `common`: Shared business logic utilities

---
---

## Tech Stack

| Technology       | Purpose                               |
|------------------|----------------------------------------|
| **Kotlin**       | Primary language                      |
| **Jetpack Compose** | Modern declarative UI framework       |
| **Hilt**         | Dependency Injection                  |
| **Retrofit**     | Networking (REST API)                 |
| **Kotlin Coroutines + Flow** | Asynchronous and reactive programming |
| **Coil**         | Image loading                         |
| **JUnit + Mockito** | Unit testing framework               |

---

## App Screenshot

Here is how the app screens looks:

<img src="screenshots/Screenshot_book_list.png" alt="BookList Screen" width="300"/>
<br/>
<img src="screenshots/Screenshot_book_details_1.png" alt="BookDetails Screen" width="300"/>
<br/>
<img src="screenshots/Screenshot_book_details_2a.png" alt="BookDetails Screen A" width="300"/>
<br/>
<img src="screenshots/Screenshot_book_details_2b.png" alt="BookDetails Screen B" width="300"/>
<br/>
