plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // ✅ Add Firebase Plugin
}

android {
    namespace = "com.example.sas"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sas"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Firebase Dependencies
    implementation("com.google.firebase:firebase-auth:22.0.0") // ✅ Firebase Authentication
    implementation("com.google.firebase:firebase-firestore:24.9.1") // ✅ Firestore Database
    implementation("com.google.firebase:firebase-storage:20.3.0") // ✅ Firebase Storage
    implementation ("com.google.firebase:firebase-auth:latest_version")
    implementation ("com.google.firebase:firebase-firestore:latest_version")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
