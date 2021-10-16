/*
 * Copyright 2020 Furkan Akdemir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import ext.applyDefault

plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
}

android {

    compileSdk = AndroidSdk.compileVersion

    defaultConfig {
        applicationId = "com.furkanakdemir.surroundcardviewsample"

        minSdk = AndroidSdk.minimumVersion
        targetSdk = AndroidSdk.targetVersion
        versionCode = AndroidSdk.version_code
        versionName = AndroidSdk.version_name
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    signingConfigs {

        create("release") {
            storeFile = project.file("scv.jks")
            storePassword = System.getenv("scvStorePassword")
            keyAlias = System.getenv("scvStoreKeyAlias")
            keyPassword = System.getenv("scvStorePasswordKeyPassword")
        }
    }

    buildTypes {

        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".dev"
            isTestCoverageEnabled = true
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {

            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
            isTestCoverageEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        createMainSourceSet()
        createTestSourceSet()
        createAndroidTestSourceSet()
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    kotlinOptions.applyDefault()
    testOptions.applyDefault()
    lintOptions.applyDefault(rootProject.file("qa/lint/lint.xml"))
}

dependencies {
    implementation(project(":surroundcardview"))
//    implementation(Deps.surroundcardview)

    implementation(Deps.kotlin)
    implementation(Deps.appcompat)
    implementation(Deps.constraintlayout)
    implementation(Deps.material)
    implementation(Deps.recyclerview)

    debugImplementation(Deps.leak_canary)

    // JUnit
    testImplementation(TestDeps.junit)
}
