plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.pb.news"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

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
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    packaging {
        resources {
            merges += "META-INF/gradle/incremental.annotation.processors"
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }

}

dependencies {
    implementation(project(":common"))
    implementation(project(":features:newsdetails"))
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.androidx.compose.ui)
    implementation(libs.bundles.hilt)
    implementation("androidx.room:room-runtime:2.5.0") {
        exclude(group = "com.intellij", module = "annotations")
    }
    kapt("androidx.room:room-compiler:2.5.0")

    implementation(libs.bundles.networking)
    testImplementation(libs.bundles.testing)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.bundles.testing)

    // Compose BOM and other dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}

configurations.all {
    resolutionStrategy {
        // Force the newer annotations version to avoid conflicts
        force("org.jetbrains:annotations:23.0.0")
    }
}
