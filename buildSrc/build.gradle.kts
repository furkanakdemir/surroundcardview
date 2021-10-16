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
plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
}

object Plugins {
    const val android = "7.0.3"
    const val kotlin = "1.5.31"
    const val versions = "0.39.0"
    const val spotless = "5.17.0"
    const val detekt = "1.18.1"
    const val ktlint = "10.2.0"
    const val dexcount = "1.0.2"
    const val bintray = "1.8.5"
}

dependencies {
    implementation(kotlin("gradle-plugin", version = Plugins.kotlin))
    implementation(kotlin("stdlib-jdk8", version = Plugins.kotlin))

    implementation("com.android.tools.build:gradle:${Plugins.android}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Plugins.kotlin}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Plugins.detekt}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${Plugins.spotless}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${Plugins.versions}")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:${Plugins.ktlint}")
}
