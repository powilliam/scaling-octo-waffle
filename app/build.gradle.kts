plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.powilliam.scalingoctowaffle"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.powilliam.scalingoctowaffle"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.powilliam.scalingoctowaffle.CustomTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }
}

dependencies {
    val firebaseBom = platform("com.google.firebase:firebase-bom:32.7.2")
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
    val roomVersion = "2.6.1"
    val workVersion = "2.9.0"

    // Firebase
    implementation(firebaseBom)
    implementation("com.google.firebase:firebase-database")

    // Jetpack Compose
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
    implementation("androidx.hilt:hilt-work:1.1.0")
    kapt("androidx.hilt:hilt-compiler:1.1.0")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:$roomVersion")
    testImplementation("androidx.room:room-testing:$roomVersion")

    // WorkManager
    implementation("androidx.work:work-runtime:$workVersion")
    implementation("androidx.work:work-runtime-ktx:$workVersion")
    androidTestImplementation("androidx.work:work-testing:$workVersion")

    // JSoup
    implementation("org.jsoup:jsoup:1.17.2")

    // Tests
    testImplementation("junit:junit:4.13.2")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.46.1")
    testImplementation("org.mockito:mockito-core:5.10.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.0")
    testImplementation("app.cash.turbine:turbine:1.0.0")

    androidTestImplementation("org.mockito:mockito-android:5.10.0")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:5.2.0")
    androidTestImplementation("app.cash.turbine:turbine:1.0.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}