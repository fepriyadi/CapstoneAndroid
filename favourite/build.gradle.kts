plugins {
    alias(libs.plugins.android.dynamic.feature)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")

}
android {
    namespace = "com.example.favourite"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

ksp {
    arg("logEpoxyTimings", "true")
    arg("validateEpoxyModelUsage", "true")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":app"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.epoxy)
    implementation(libs.epoxy.data.binding)
    ksp(libs.epoxy.processor)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.androidx.room)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx.android)
    implementation(libs.room.testing)
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}