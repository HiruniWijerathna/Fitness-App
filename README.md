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

**Fitness-App** is a modern Android fitness application designed to help users track daily physical activities, manage workout routines, monitor progress, and achieve their fitness goals.

The application provides a complete offline fitness experience, allowing users to monitor workouts, track calories, count steps, manage personal fitness data, and stay motivated through achievement-based rewards.

### Architecture

- 📱 Native Android Development
- 🎨 Material Design Components
- 💾 Offline-First Data Storage
- 🏃 Activity & Workout Tracking
- 🎥 Video-Based Workout Guidance
- 📊 Progress Monitoring System

---

# 🌟 Key Features

## 🏠 Home Dashboard

![Calories](https://img.shields.io/badge/-Calories%20Tracking-red)
![Steps](https://img.shields.io/badge/-Step%20Counter-green)
![Workout](https://img.shields.io/badge/-Workout%20Time-blue)
![Water](https://img.shields.io/badge/-Water%20Tracker-cyan)

### Features

- Personalized greeting
- Daily calorie progress ring
- Daily steps tracking ring
- Workout duration tracking
- Water intake tracker
- Fitness progress overview

---

## 💪 Workout Management

![Workout](https://img.shields.io/badge/-Workout%20Programs-blue)
![Search](https://img.shields.io/badge/-Real%20Time%20Search-purple)
![Video](https://img.shields.io/badge/-Video%20Workouts-orange)

### Workout Categories

- Popular Workouts
- Full Body Workouts
- Arm Workouts
- Leg Workouts
- ABS Workouts
- Yoga Sessions

### Features

- Browse workout programs
- Search workouts instantly
- View workout details
- Track workout completion
- Guided workout videos
- Structured fitness challenges

---

## 📋 Workout Detail Screen

![Details](https://img.shields.io/badge/-Workout%20Details-green)
![Checklist](https://img.shields.io/badge/-Checklist-orange)

### Features

- Dynamic workout information
- Large hero workout image
- Sub-workout checklist
- Progress tracking
- Start Challenge button
- Workout breakdown view

---

## 🎥 Video Workout Player

![Player](https://img.shields.io/badge/-Video%20Player-blue)
![Progress](https://img.shields.io/badge/-Progress%20Tracking-green)
![Timer](https://img.shields.io/badge/-Workout%20Timer-orange)

### Features

- Android VideoView player
- Play / Pause controls
- Interactive seek bar
- Real-time workout timer
- Smooth playback tracking
- Up Next workout recommendations

---

## 🏆 Achievements & Progress

![Badge](https://img.shields.io/badge/-Achievement%20Badges-gold)
![Goals](https://img.shields.io/badge/-Goal%20Tracking-green)

### Achievement System

- First Workout Badge
- 10 Workouts Completed Badge
- 30-Day Streak Badge
- Goal Reached Badge
- Personal Best Statistics
- Activity Timeline

### Progress Tracking

- Total workouts completed
- Workout duration statistics
- Calories burned summary
- Weekly performance highlights

---

## 👤 User Profile Management

![Profile](https://img.shields.io/badge/-Profile%20Management-blue)
![Photo](https://img.shields.io/badge/-Photo%20Upload-purple)

### Profile Features

- Edit Name
- Edit Height
- Edit Weight
- Edit Birthday
- Upload Profile Photo
- Select Activity Level

### Activity Levels

- Sedentary
- Lightly Active
- Moderately Active
- Active
- Very Active

---

# 💾 Local Data Management

## SharedPreferences Storage

The application uses Android SharedPreferences as a lightweight local database.

### Stored Information

- User Name
- Height
- Weight
- Birthday
- Activity Level
- Profile Preferences
- Profile Image Path

---

## File Storage Management

### Profile Image Handling

- Image selection from gallery
- Internal storage saving using `filesDir`
- Persistent image access
- Global image synchronization

The profile image is automatically displayed across:

- Home Screen
- Workout Screen
- Achievement Screen
- Profile Screen

---

# ⚙️ Backend Logic & Features

## 🔍 Real-Time Search Filter

Implemented using:

- TextWatcher
- Dynamic UI visibility updates
- Instant workout filtering

### Process

1. User enters search text
2. Workout titles are matched
3. Matching cards remain visible
4. Non-matching cards are hidden

---

## 🎥 Asynchronous Media Tracking

Implemented using:

- Handler
- Runnable

### Features

- Updates every 500ms
- Real-time timer synchronization
- Smooth progress bar movement
- Video playback monitoring

---

## 🔄 Intent-Based Routing

Data is transferred between activities using Intent Extras.

### Shared Data

- Workout Titles
- Workout Images
- Video Resources
- Workout Categories
- Up Next Recommendations

---

# 🎨 UI/UX Design

## Glassmorphism Design

- Dark theme
- Floating translucent cards
- Modern fitness interface
- Material Design styling

## Responsive Layouts

- ConstraintLayout architecture
- Mobile-friendly UI
- Optimized rendering
- Consistent navigation experience

---

# 🛠️ Tech Stack

| Layer | Technology |
|---------|------------|
| Language | Kotlin |
| Platform | Android |
| UI | XML |
| Layouts | ConstraintLayout |
| Design | Material Design Components |
| Storage | SharedPreferences |
| File System | Internal Storage |
| Media Playback | VideoView |
| Navigation | BottomNavigationView |

---

# 📂 Project Structure

```plaintext
FITNESS_APP/
├── app/
│
├── java/
│   ├── HomeActivity.kt
│   ├── WorkoutActivity.kt
│   ├── WorkoutDetailActivity.kt
│   ├── StartWorkoutActivity.kt
│   ├── AchievementActivity.kt
│   └── ProfileActivity.kt
│
├── res/
│   ├── layout/
│   ├── drawable/
│   ├── mipmap/
│   ├── values/
│   └── menu/
│
├── SharedPreferences
├── Internal Storage
└── AndroidManifest.xml
```

---

# 🚀 Getting Started

```bash
# Clone repository
git clone https://github.com/HiruniWijerathna/Fitness-App.git

# Open project in Android Studio

# Sync Gradle

# Run application on Emulator or Physical Device
```

---

# 📱 Main Screens

- 🏠 Home Dashboard
- 💪 Workout Hub
- 📋 Workout Details
- 🎥 Workout Player
- 🏆 Achievements
- 👤 User Profile

---

# 🔮 Future Enhancements

- Firebase Authentication
- Cloud Data Synchronization
- Google Fit Integration
- Smart Calorie Prediction
- AI Workout Recommendations
- Push Notifications
- Social Fitness Challenges
- Fitness Analytics Dashboard

---

# 🎥 Demo

### 📹 Project Demo Video

Add your YouTube or Google Drive link here.

---

# 📸 Screenshots

```md
![Home Screen](screenshots/home.png)
![Workout Screen](screenshots/workout.png)
![Achievement Screen](screenshots/achievement.png)
![Profile Screen](screenshots/profile.png)
```

---

# 📄 Conclusion

Fitness-App is a complete Android fitness management application that combines workout tracking, activity monitoring, achievement management, media playback, and profile customization into a single platform. Built using Kotlin and Android native technologies, the application provides a modern user experience while operating entirely offline through efficient local storage and state management.
