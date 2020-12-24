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
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    jcenter()
    maven("https://plugins.gradle.org/m2/")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

object Plugins {
    const val android = "4.1.1"
    const val kotlin = "1.4.21"
    const val versions = "0.28.0"
    const val spotless = "3.28.1"
    const val detekt = "1.8.0"
    const val ktlint = "9.2.1"
    const val dexcount = "1.0.2"
    const val bintray = "1.8.5"
}

dependencies {
    // Explicitly declare all the kotlin bits to avoid mismatched versions
    implementation(kotlin("gradle-plugin", version = Plugins.kotlin))
    implementation(kotlin("stdlib", version = Plugins.kotlin))
    implementation(kotlin("stdlib-common", version = Plugins.kotlin))
    implementation(kotlin("stdlib-jdk7", version = Plugins.kotlin))
    implementation(kotlin("stdlib-jdk8", version = Plugins.kotlin))
    implementation(kotlin("reflect", version = Plugins.kotlin))

    implementation("com.android.tools.build:gradle:${Plugins.android}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Plugins.kotlin}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Plugins.detekt}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${Plugins.spotless}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${Plugins.versions}")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:${Plugins.ktlint}")
}
