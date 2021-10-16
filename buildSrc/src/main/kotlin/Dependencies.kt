/*
 * Copyright 2021 Furkan Akdemir
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
object Versions {

    const val surroundcardview = "1.0.6"
    const val kotlin_version = "1.5.31"

    const val constraintlayout = "2.1.1"
    const val appcompat = "1.3.1"
    const val material = "1.4.0"
    const val recyclerview = "1.2.1"
    const val core_ktx = "1.6.0"

    const val junit = "4.13.2"

    const val leak_canary = "2.7"

    const val ktlint_internal = "0.42.1"

    const val nexus_publishing = "1.1.0"
}

object AndroidSdk {

    const val minimumVersion = 23
    const val compileVersion = 31
    const val targetVersion = 31
    const val version_code = 10006
    const val version_name = "1.0.6"
}

object Deps {

    const val surroundcardview = "com.furkanakdemir:surroundcardview:${Versions.surroundcardview}"

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin_version}"

    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"

    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

    const val material = "com.google.android.material:material:${Versions.material}"

    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"

    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"

    const val leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.leak_canary}"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "android"
    const val kotlinKapt = "kapt"
    const val nexusPublishing = "io.github.gradle-nexus.publish-plugin"
}


object TestDeps {
    const val junit = "junit:junit:${Versions.junit}"
}
