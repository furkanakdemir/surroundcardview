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

import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.LibraryVariant
import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.DomainObjectSet
import org.gradle.api.NamedDomainObjectContainer

private const val RELEASE = "release"
private const val DEBUG = "debug"

fun NamedDomainObjectContainer<BuildType>.generateApplicationOutputs(
    domainObjectSet: DomainObjectSet<ApplicationVariant>
) = domainObjectSet.generateApplicationOutputs(this)

fun NamedDomainObjectContainer<BuildType>.generateLibraryOutputs(
    domainObjectSet: DomainObjectSet<LibraryVariant>
) = domainObjectSet.generateLibraryOutputs(this)

internal inline val NamedDomainObjectContainer<BuildType>.getRelease: String
    get() = getByName(RELEASE).name

internal inline val NamedDomainObjectContainer<BuildType>.getDebug: String
    get() = getByName(DEBUG).name
