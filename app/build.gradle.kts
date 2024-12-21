plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.lab3_urls"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lab3_urls"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Зависимости для Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // Зависимость для Coroutines
    implementation(libs.kotlinx.coroutines.android)
    // CardView
    implementation(libs.androidx.cardview)
    // Material Components
    implementation(libs.material)
    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.gridlayout)
    // Другие зависимости, которые уже есть
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Тестовые зависимости
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.kotlinx.coroutines.android.v164)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // Зависимость для Coroutines
    implementation(libs.kotlinx.coroutines.android)
}
