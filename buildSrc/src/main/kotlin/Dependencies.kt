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
object Versions {

    const val kotlin_version = "1.4.21"

    const val constraintlayout = "2.0.4"
    const val appcompat = "1.2.0"
    const val material = "1.2.1"
    const val recyclerview = "1.1.0"
    const val core_ktx = "1.3.2"

    const val junit = "4.13.1"

    const val leak_canary = "2.5"

    const val ktlint_internal = "0.40.0"
}

object PluginVersions {
    const val versions = "0.28.0"
    const val spotless = "3.28.1"
    const val detekt = "1.8.0"
    const val ktlint = "9.2.1"
    const val dexcount = "1.0.2"
    const val bintray = "1.8.5"
}

object AndroidSdk {

    const val sdk_minimum = 23
    const val sdk_compile = 30
    const val sdk_target = 30
    const val version_code = 10002
    const val version_name = "1.0.2"
}

object Deps {

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
    const val dexcount = "com.getkeepsafe.dexcount"
    const val bintray = "com.jfrog.bintray"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val versions = "com.github.ben-manes.versions"
    const val spotless = "com.diffplug.gradle.spotless"
}

object TestDeps {
    const val junit = "junit:junit:${Versions.junit}"
}
