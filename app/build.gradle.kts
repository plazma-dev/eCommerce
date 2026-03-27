plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.dagger.hilt.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {
    namespace = "com.neda.ecommerce"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.neda.ecommerce"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
        resValues = true
    }

    flavorDimensions += "partner"

    productFlavors {
        create("defaultPartner") {
            dimension = "partner"
        }

        create("partnerA") {
            dimension = "partner"
            applicationIdSuffix = ".partnera"
            resValue("string", "app_name", "Beauty shop")
        }
        create("partnerB") {
            dimension = "partner"
            applicationIdSuffix = ".partnerb"
            resValue("string", "app_name", "Pharmacy")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.swiperefreshlayout)
    ksp(libs.hilt.android.compiler.v2571)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.glide)
    ksp(libs.glide.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}