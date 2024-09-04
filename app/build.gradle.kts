plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.capstonemovie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.capstonemovie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            isShrinkResources = true
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
    lint {
        baseline = file("lint-baseline.xml")
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
//    implementation(libs.core.ktx)
    implementation(libs.feature.delivery.ktx)
    ksp(libs.epoxy.processor)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.jvm)
    implementation(libs.androidx.room)
    implementation(libs.room.ktx.android)
    implementation(libs.room.testing)
    ksp(libs.room.compiler)
    implementation(libs.glide)
    implementation(libs.multidex)
    ksp(libs.glide.compiler)
    implementation(libs.lifecycle.viewmodel)

//    ksp(libs.room.compiler)
    testImplementation(libs.junit)
    debugImplementation(libs.leak.canary)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}