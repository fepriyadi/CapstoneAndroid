import org.gradle.initialization.Environment
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.capstoneMovie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.capstoneMovie"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    dynamicFeatures += setOf(":favourite")
}

ksp {
    arg("logEpoxyTimings", "true")
    arg("validateEpoxyModelUsage", "true")
}

dependencies {
    implementation(project(":core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.epoxy)
    implementation(libs.epoxy.data.binding)
    implementation(libs.rxBinding)
    implementation(libs.rxRelay)
    ksp(libs.epoxy.processor)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.androidx.room)
    implementation(libs.room.ktx.android)
    implementation(libs.room.testing)
    implementation(libs.glide)
    ksp(libs.glide.compiler)

//    ksp(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}