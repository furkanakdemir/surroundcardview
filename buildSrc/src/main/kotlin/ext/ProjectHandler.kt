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
package ext

import AndroidTest
import BuildTypeFactory
import Main
import SigningFactory
import Test
import com.android.build.gradle.api.AndroidSourceSet
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.SigningConfig
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import utils.gitSha

fun Project.createRelease(
    buildTypeContainer: NamedDomainObjectContainer<BuildType>,
    signingContainer: NamedDomainObjectContainer<SigningConfig>,
    isLibrary: Boolean = false
) = BuildTypeFactory.createRelease(this, buildTypeContainer, signingContainer, isLibrary)

fun Project.createDebug(
    buildTypeContainer: NamedDomainObjectContainer<BuildType>,
    isLibrary: Boolean = false
) = BuildTypeFactory.createDebug(this, buildTypeContainer, isLibrary)

fun Project.createKotlinMain(
    namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
) = Main.create(namedDomainObjectContainer, this)

fun Project.createKotlinTest(
    namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
) = Test.create(namedDomainObjectContainer, this)

fun Project.createKotlinAndroidTest(
    namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>
) = AndroidTest.create(namedDomainObjectContainer, this)

fun Project.createMain(buildTypeContainer: NamedDomainObjectContainer<AndroidSourceSet>) =
    Main.create(buildTypeContainer, this)

fun Project.createAndroidTest(buildTypeContainer: NamedDomainObjectContainer<AndroidSourceSet>) =
    AndroidTest.create(buildTypeContainer, this)

fun Project.createTest(buildTypeContainer: NamedDomainObjectContainer<AndroidSourceSet>) =
    Test.create(buildTypeContainer, this)

fun Project.createReleaseSigning(signingContainer: NamedDomainObjectContainer<SigningConfig>) =
    SigningFactory.create(this, signingContainer)

internal inline val Project.gitSha: String get() = gitSha(this)
