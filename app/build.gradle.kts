import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.hb.test"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hb.test"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // run cucumber tests
        // testApplicationId = "com.hb.test.cucumber"
        // testInstrumentationRunner = "com.hb.test.cucumber.CucumberTestInstrumentation"

        // run compose ui tests with Hilt
        testInstrumentationRunner = "com.hb.test.ui.HiltTestRunner"

        // run normal compose ui tests
        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        // load the values from .properties file
        val keystoreFile = project.rootProject.file("keys.properties")
        val properties = Properties().apply { load(keystoreFile.inputStream()) }
        val apiAuthToken = properties.getProperty("TMDB_AUTH_TOKEN") ?: ""

        buildConfigField(type = "String", name = "TMDB_AUTH_TOKEN", value = apiAuthToken)
    }

    buildFeatures.buildConfig = true

    buildTypes {
        debug {
            isShrinkResources = false
            isMinifyEnabled = false
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.nav.controller)
    implementation(libs.androidx.nav.compose)
    implementation(libs.timber)
    implementation(libs.okhttp.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.coil)
    implementation(libs.lottie)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.paging)
    implementation(libs.androidx.datastore)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mock.web.server)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine.test)
    testImplementation(libs.paging.test)
    testImplementation(libs.truth.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.compose.junit4)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.android.cucumber)
    androidTestImplementation(libs.android.cucumber.pico)
    androidTestImplementation(libs.hilt.test)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
