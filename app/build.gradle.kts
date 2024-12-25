plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.aliosman.kelimeoyunuprojesi"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.aliosman.kelimeoyunuprojesi"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "Beta 3.3.0"

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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}



dependencies {

    val nav_version = "2.7.7"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("com.google.android.material:material:1.12.0")

    //Corotines
    // Kotlin Coroutines Core Kütüphanesi
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    // Kotlin Coroutines Android Kütüphanesi
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

}