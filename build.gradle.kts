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

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

plugins.apply(BuildPlugins.DETEKT)
plugins.apply(BuildPlugins.SPOTLESS)
plugins.apply(BuildPlugins.KTLINT)
plugins.apply(BuildPlugins.VERSIONS)
plugins.apply(BuildPlugins.UPDATE_DEPENDENCIES)

allprojects {
    repositories.applyDefaults()
}

tasks.registering(Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register<GradleBuild>("checkCodeStyle") {
    tasks = listOf("ktlintFormat", "detekt", "spotlessApply", "testDebugUnitTest", "lintDebug")
}
