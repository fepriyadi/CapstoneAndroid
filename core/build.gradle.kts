plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "API_KEY", "\"e3ea2cdc09b8b8695cedb54c0e54affd\"")
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.retrofit2.core)
    implementation(libs.retrofit2.converter)
    implementation(libs.retrofit2.logging)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.room)
    implementation(libs.room.ktx.android)
    implementation(libs.room.testing)
    ksp(libs.room.compiler)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.cardview)
    implementation(libs.recyclerview)
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.jvm)

    implementation(libs.epoxy)
    implementation(libs.epoxy.data.binding)
    ksp(libs.epoxy.processor)

    implementation(libs.sql.lite)
    implementation(libs.sql.chiper)

    implementation(libs.multidex)

    api(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}