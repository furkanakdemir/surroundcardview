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
import ext.applyDefaultTestConfigs
import ext.createDebug
import ext.createKotlinAndroidTest
import ext.createKotlinMain
import ext.createKotlinTest
import ext.createRelease
import ext.createReleaseSigning
import ext.setDefaults

plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
}

android {

    compileSdkVersion(AndroidSdk.sdk_compile)

    defaultConfig {
        applicationId = "com.furkanakdemir.surroundcardviewsample"
        minSdkVersion(AndroidSdk.sdk_minimum)
        targetSdkVersion(AndroidSdk.sdk_target)
        versionCode = AndroidSdk.version_code
        versionName = AndroidSdk.version_name
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    signingConfigs {
        createReleaseSigning(this)
    }

    buildTypes {
        createDebug(this)
        createRelease(this, signingConfigs)
    }

    sourceSets {
        createKotlinMain(this)
        createKotlinTest(this)
        createKotlinAndroidTest(this)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions.applyDefaultTestConfigs()

    lintOptions.setDefaults()
}

dependencies {
    implementation(project(":surroundcardview"))

    implementation(Deps.kotlin)
    implementation(Deps.appcompat)
    implementation(Deps.constraintlayout)
    implementation(Deps.material)
    implementation(Deps.recyclerview)

    debugImplementation(Deps.leak_canary)

    // JUnit
    testImplementation(TestDeps.junit)
}
