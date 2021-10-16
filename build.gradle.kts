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
import plugins.BuildPlugins
import java.io.FileInputStream
import java.util.Properties

plugins {
    id(Plugins.nexusPublishing).version(Versions.nexus_publishing)
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins.apply(BuildPlugins.DETEKT)
plugins.apply(BuildPlugins.SPOTLESS)
plugins.apply(BuildPlugins.KTLINT)
plugins.apply(BuildPlugins.VERSIONS)

val properties = Properties()
val fis = FileInputStream(rootProject.file("local.properties"))
properties.load(fis)

val sonatypeUsername: String by lazy { properties.getProperty("sonatypeUsername") }
val sonatypePassword: String by lazy { properties.getProperty("sonatypePassword") }
val sonatypeStagingProfileId: String by lazy { properties.getProperty("sonatypeStagingProfileId") }

nexusPublishing {
    repositories {

        val newNexusUrl = "https://s01.oss.sonatype.org/service/local/"
        val snapshotUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"

        sonatype {
            username.set(sonatypeUsername)
            password.set(sonatypePassword)
            nexusUrl.set(uri(newNexusUrl))
            snapshotRepositoryUrl.set(uri(snapshotUrl))
            stagingProfileId.set(sonatypeStagingProfileId)
        }
    }
}

allprojects {
    repositories.applyDefault()
}

tasks.registering(Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register<GradleBuild>("checkCodeStyle") {
    tasks = listOf("ktlintFormat", "detekt", "spotlessApply", "testDebugUnitTest", "lintDebug")
}
