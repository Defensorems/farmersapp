plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.farmersapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.farmersapp"
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
        // Существующие зависимости
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.constraintlayout)
        implementation(libs.navigation.fragment)
        implementation(libs.navigation.ui)

        // Добавьте новые зависимости
        implementation(libs.lifecycle.viewmodel)
        implementation(libs.lifecycle.livedata)
        implementation(libs.lifecycle.viewmodel.ktx)
        implementation(libs.lifecycle.runtime.ktx)
        implementation(libs.recyclerview)
        implementation(libs.coroutines.android)
        implementation(libs.coroutines.core)

        // Тестовые зависимости
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}