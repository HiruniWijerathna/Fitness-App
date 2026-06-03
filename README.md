# 🏋️ Fitness-App – Android Fitness Tracking & Workout Management System

<p align="center">
  <img src="banner.png" alt="Fitness App Banner" width="100%" />
</p>

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Material Design](https://img.shields.io/badge/Material%20Design-757575?style=for-the-badge&logo=materialdesign&logoColor=white)
![SharedPreferences](https://img.shields.io/badge/SharedPreferences-Local%20Storage-blue?style=for-the-badge)
![VideoView](https://img.shields.io/badge/VideoView-Media%20Player-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

---

## 🌟 Project Overview

**Fitness-App** is a modern Android fitness tracking application designed to help users monitor daily physical activities, manage workout routines, and achieve fitness goals.

The app provides a complete offline fitness experience including workout tracking, video-based exercises, progress monitoring, achievements, and personal profile management.

The system follows:

- 📱 Native Android Architecture (Kotlin + XML)
- 🎨 Material Design UI
- 💾 Offline-first Local Storage
- 🏃 Workout & Activity Tracking
- 🎥 Video-based Workout Guidance

---

## 💪 Key Features

## 🏠 Home Dashboard

- Daily calorie tracking
- Step counter progress rings
- Workout time tracking
- Water intake tracker
- Personalized greeting system

---

## 💪 Workout Module

- Categorized workouts
- Real-time search filter
- Workout detail view
- Checklist-based progress tracking
- Start workout navigation

---

## 🎥 Video Workout Player

- Built-in VideoView player
- Play / Pause controls
- Seek bar progress tracking
- Real-time timer update
- “Up Next” workout preview

---

## 🏆 Achievements System

- Fitness badges:
  - First Workout
  - 10 Workouts
  - 30 Day Streak
  - Goal Reached
- Personal best tracking
- Activity timeline history

---

## 👤 Profile Management

- Edit personal details:
  - Name
  - Height
  - Weight
  - Birthday
- Upload profile image
- Activity level selection:

---

## 🛠️ Tech Stack

| Layer | Technology |
|------|------------|
| Language | Kotlin |
| UI | XML (ConstraintLayout) |
| Design | Material Design |
| Storage | SharedPreferences |
| File System | Internal Storage (filesDir) |
| Media Player | VideoView |
| Navigation | BottomNavigationView |

---

## 🧠 Architecture

### Frontend
- XML layouts
- Material UI components
- ConstraintLayout responsive design
- Bottom navigation system

### Backend (Local Logic)
- SharedPreferences for data storage
- File I/O for profile images
- Intent-based navigation
- Handler + Runnable for video progress
- TextWatcher for search filtering

---

## 📂 Project Structure

```plaintext
Fitness-App/
│
├── HomeActivity
├── WorkoutActivity
├── WorkoutDetailActivity
├── StartWorkoutActivity
├── AchievementActivity
├── ProfileActivity
│
├── SharedPreferences (fitness_app_prefs)
├── Internal Storage (profile images)
└── Video Player Module
```

---

## 🚀 Getting Started

```bash
# Clone repository
git clone https://github.com/HiruniWijerathna/Fitness-App

# Open in Android Studio

# Sync Gradle

# Run on emulator or physical device
```

---

## 🔮 Future Enhancements

- Firebase authentication
- Cloud sync
- Google Fit integration
- AI workout recommendations
- Smart calorie prediction
- Push notifications
- Social fitness challenges

---


## 📄 Conclusion

Fitness-App is a complete offline Android fitness application that combines workout tracking, activity monitoring, video-based training, achievements, and profile management into one system. It is built using Kotlin and follows a clean, modular architecture for scalability and performance.
