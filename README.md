# 💇 Sakhi & Sakha Studio and Academy

**An Android-Based Parlour Management and Beauty Academy Application**

Sakhi & Sakha Studio and Academy is an Android application developed to simplify and digitize parlour management operations. It provides a convenient platform for customers to explore beauty services, book appointments, manage their profiles, and access academy-related information.

The application also provides an admin panel to manage appointments, services, and staff efficiently.

## 📱 Project Overview

The application aims to reduce manual work in beauty parlour management by providing a digital solution for appointment booking, service management, and customer interaction.

## ✨ Key Features

### 👤 User Module
- User registration and login.
- Secure authentication using Firebase.
- Browse available beauty and salon services.
- Book appointments.
- View personal appointment details.
- Track appointment status.
- Manage user profile.
- Access academy and course information.

### 🛠️ Admin Module
- Admin login and dashboard.
- View customer appointments.
- Manage appointment requests.
- Approve or reject appointments.
- Manage salon services.
- Manage staff information.
- View appointment details.

### 💅 Additional Features
- Attractive and user-friendly interface.
- Splash screen with welcome animation.
- Separate sections for male and female services.
- Academy information and course management.
- Firebase integration for authentication and data storage.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Android application development |
| XML | UI design |
| Android Studio | Development environment |
| Firebase Authentication | User authentication |
| Cloud Firestore | Database management |
| RecyclerView | Displaying dynamic lists |
| Gradle | Build automation |

## 🏗️ Application Architecture

The application follows an Android-based client architecture integrated with Firebase services.

- **Frontend:** Java and XML
- **Backend Services:** Firebase
- **Database:** Cloud Firestore
- **Authentication:** Firebase Authentication

## 📂 Project Structure

```text
Sakhi-Sakha-App/
│
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/sas/
│   │       │       ├── MainActivity.java
│   │       │       ├── HomeActivity.java
│   │       │       ├── RegistrationActivity.java
│   │       │       ├── AdminDashboardActivity.java
│   │       │       ├── Appointment.java
│   │       │       └── ...
│   │       │
│   │       ├── res/
│   │       │   ├── drawable/
│   │       │   ├── layout/
│   │       │   ├── mipmap/
│   │       │   └── values/
│   │       │
│   │       └── AndroidManifest.xml
│   │
│   └── build.gradle.kts
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 🚀 Getting Started

Follow these steps to run the project locally.

### Prerequisites

- Android Studio
- Android SDK
- JDK
- Internet connection
- Firebase account

### Installation

1. Clone this repository:

```bash
git clone https://github.com/Yuvi288/Sakhi-Sakha-App.git
```

2. Open Android Studio.

3. Select **Open** and choose the cloned project folder.

4. Allow Gradle to sync and download the required dependencies.

5. Configure Firebase for your application using your own Firebase project configuration.

6. Run the application on an Android emulator or physical Android device.

## 🔥 Firebase Configuration

This project uses Firebase Authentication and Cloud Firestore.

To configure Firebase:

1. Create a Firebase project.
2. Register your Android application using its package name.
3. Enable Firebase Authentication.
4. Create a Cloud Firestore database.
5. Configure the required Firestore security rules.
6. Add your Firebase configuration file to the app module.

**Security Note:** Never upload Firebase service account private keys, API secrets, or other sensitive credentials to a public repository.

## 🎯 Project Objectives

- Digitize traditional parlour management operations.
- Simplify appointment booking.
- Improve customer experience.
- Reduce manual appointment management.
- Provide efficient administration.
- Support beauty academy information and course management.

## 🔮 Future Enhancements

- Online payment gateway integration.
- Push notifications for appointment updates.
- Appointment reminders.
- Advanced analytics and reports.
- Customer feedback and ratings.
- Improved staff scheduling.
- Online course registration.

## 👨‍💻 Developer

**Yuvraj Jijaram Shirsath**

Computer Science Engineering Student

GitHub: [@Yuvi288](https://github.com/Yuvi288)

## 📌 Repository

[https://github.com/Yuvi288/Sakhi-Sakha-App](https://github.com/Yuvi288/Sakhi-Sakha-App)

---

⭐ If you find this project useful, consider giving this repository a star!

**Developed with ❤️ using Android Studio and Firebase.**
