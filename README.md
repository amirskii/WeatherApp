# Weather App
API used:
1. Open Meteo(https://open-meteo.com/)

Following technologies have been used:
1. Architecture Components (Compose, Coroutine/Flow, ViewModel, Navigation)
2. MVVM, Koin for dependency injection
3. Kotlin written
4. SOLID, Clean Architecture

## Clean Architecture
Although challenge project is tiny I tried to separate business logic, presentation and data layers as best as possible.

### Layers
- **Domain** - Contains the business logic of the application: use cases and domain models.
- **Data** - Network interraction and data models.
- **Presentation** - A layer that interacts with the UI and Android framework has also own models. 

## Unit-tests
Layers covered:
- ViewModels
- Use cases
- Repository
- Data transformation

## Screenshots
[<img src="/screenshots/weather1.png" align="left" width="200" hspace="10" vspace="10">](/screenshots/weather1.png)

### Unit tests
[<img src="/screenshots/tests.png" align="left" width="400" hspace="30" vspace="10">](/screenshots/tests.png)
