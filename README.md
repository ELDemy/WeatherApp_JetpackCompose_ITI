# 🌦 Weezy – Weather Forecast App

<p align="center">
  <img
    width="180"
    alt="Weezy App Icon"
    src="src/app/src/main/res/drawable/app_icon.png"
  />
</p>

<p align="center">
  <strong>Real-time weather forecasts, smart alerts, and location-based insights.</strong>
</p>

<p align="center">
  <a href="#overview">Overview</a> •
  <a href="#features">Features</a> •
  <a href="#screenshots">Screenshots</a> •
  <a href="#architecture">Architecture</a> •
  <a href="#tech-stack">Tech Stack</a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green"/>
  <img src="https://img.shields.io/badge/Kotlin-2.3-blue"/>
  <img src="https://img.shields.io/badge/Architecture-MVVM-orange"/>
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-purple"/>
</p>

---

## 📋 Overview

Weezy is a modern Android Weather Forecast application developed as part of the  
**ITI (Information Technology Institute) 9-Month Mobile Application Development – Native Android Program**.

The application follows production-grade Android development standards using **MVVM architecture** and modern Android libraries.

It demonstrates:

- Clean layered architecture
- Advanced state handling in Jetpack Compose
- Background scheduling with WorkManager & AlarmManager
- Offline-first persistence with Room
- Multi-language support
- Unit testing & testable business logic

---

## ✨ Features

| Feature | Description |
|----------|------------|
| 🌡 **Current Weather** | Real-time temperature, humidity, pressure, wind speed, and cloud coverage |
| 🕒 **Hourly Forecast** | Detailed hourly breakdown for the current day |
| 📅 **5-Day Forecast** | Extended multi-day weather outlook |
| ⭐ **Favorites** | Save and manage multiple locations |
| 🗺 **Map Selection** | Choose locations directly from Google Maps |
| 📍 **GPS Support** | Detect current location automatically |
| 🌍 **Multi-Language** | Supports multiple languages |
| 🔔 **Weather Alerts** | Custom notifications or alarm-based weather alerts |
| ⚙ **Custom Units** | Kelvin, Celsius, Fahrenheit + multiple wind speed units |

---

## 📱 Screenshots

### ⛅Weather

| | |
|:--:|:--:|
| <img width="400" src="https://github.com/user-attachments/assets/cba5a031-1eba-45bd-b020-c272bafca276"/>| ![Home](https://github.com/user-attachments/assets/053e26b0-900c-493b-8fbf-8c6bbac4d2ad) |

### ⛅⚙  Weather with Different Settings

|        Fahrenheit with English Language           |       Kelvin with Spanish Language             |      Celsius with Arabic Language  (RTL)     |
|:------------------------------------:|:--------------------------------------------------:|:------------------------------------------------------------------------:|
| ![Mountain view](https://github.com/user-attachments/assets/2451b1e5-8780-43c3-9cb0-bbe45d295f28) | ![Spain](https://github.com/user-attachments/assets/56e5b7c7-4e57-437f-b60d-a04c0dcfd940)   |   ![Arabic](https://github.com/user-attachments/assets/b79e5d03-a113-40da-bbd2-ab9937e458f3)|


###  📍Maps & ❤️ Favorites

| Maps | Favorite Locations |
|:------------------:|:------------------:|
| <img width="350" src="https://github.com/user-attachments/assets/5c90f859-c068-407a-bd0b-bc1ec14ccb17"/> | <img width="350" src="https://github.com/user-attachments/assets/c63592b7-5c58-4193-a607-9c08e4feffbf"/> |


### ⚙ Settings

| English Settings | Arabic Settings |
|:------------------:|:------------------:|
|<img width="350" src="https://github.com/user-attachments/assets/84e576d7-2daf-432c-af8e-8abdc197ed9e"/>|<img width="350" src="https://github.com/user-attachments/assets/0e049d05-1598-428d-b97b-42e4ccad4ddf"/> |

### 🌐 Languages & ⏰ Alerts

| Languages | Alerts | 
|:------------------:|:------------------:|
|<img width="350" src="https://github.com/user-attachments/assets/55b36937-1433-40a9-ba17-50934729600c"/>|<img width="350" src="https://github.com/user-attachments/assets/62c6ab83-f28c-4e44-8f92-519b1159b636"/>|

### 🔔 Notifications

| Notifications | Notifications | 
|:------------------:|:------------------:|
|<img width="350" src="https://github.com/user-attachments/assets/b406b3f2-0743-43c8-9f6c-19dd4e1618c5"/>|<img width="350" src="https://github.com/user-attachments/assets/f575503e-5bac-4bf4-bb09-0d227b19209b"/>|

### 👌 Permissions, Error Handling

| Warnings | Permission | 
|:------------------:|:------------------:|
|<img width="350" src="https://github.com/user-attachments/assets/2ee324b2-8812-4166-b0fc-0df75eee72a1"/>|<img width="350" src="https://github.com/user-attachments/assets/9dc55df6-e78a-4fde-8722-cd3781a5d760"/>|

---


## 🏗 Architecture

This project implements **MVVM (Model–View–ViewModel)** with clear separation of concerns:
```
Presentation Layer
   (Compose UI + ViewModels)
            │
            ▼
Repository Layer
        │            │
        ▼            ▼
Local Database   Remote API
(Room, DataStore) (Retrofit)
```


### Architectural Highlights

- Repository pattern
- DTO → Domain model mapping
- Coroutines for async operations
- Lifecycle-aware state management
- Background work scheduling
- Testable ViewModels

---

## 📂 Project Structure

```plaintext
com.dmy.weather
│
├── data
│   ├── data_source
│   ├── db
│   ├── network
│   ├── enums
│   ├── dto
│   ├── mapper
│   ├── model
│   └── repo
│
├── di
│
├── platform
│
└── presentation
    ├── alerts_screen
    ├── app_bar
    ├── components
    ├── favorites_screen
    ├── home_screen
    ├── language_selection_screen
    ├── location_search_screen
    ├── my_app
    ├── permissions
    ├── settings_screen
    ├── splash_screen
    ├── utils
    └── weather_details_screen
```
## 🛠️ Tech Stack

| Category                 | Technology                                       |
| ------------------------ | ------------------------------------------------ |
| **Language**             | Kotlin                                           |
| **Platform**             | Android SDK (Min SDK 26, Target SDK 36)          |
| **Architecture**         | MVVM (Model–View–ViewModel) + Clean Architecture |
| **UI**                   | Jetpack Compose + Material 3                     |
| **State Management**     | ViewModel + StateFlow                            |
| **Asynchronous**         | Kotlin Coroutines + Flow                         |
| **Local Database**       | Room Database                                    |
| **Preferences**          | DataStore                                        |
| **Network**              | Retrofit 2 + Gson Converter                      |
| **Dependency Injection** | Koin                                             |
| **Background Work**      | WorkManager + AlarmManager                       |
| **Maps & Location**      | Google Maps Compose + Location Services          |
| **Testing**              | JUnit, Robolectric, MockK                        |

 
## 🌍 API Reference

Weather data is fetched from **OpenWeather API**:

https://api.openweathermap.org/data/2.5/forecast

### 📦 SDK Configuration

- compileSdk: 36

- targetSdk: 36

- minSdk: 26

---

## 👨‍💻 Author

**Mahmoud ELDemerdash**

[![GitHub](https://img.shields.io/badge/GitHub-ELDemy-181717?style=flat&logo=github)](https://github.com/ELDemy)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-ELDemy-0A66C2?style=flat&logo=linkedin)](https://linkedin.com/in/ELDemy)



