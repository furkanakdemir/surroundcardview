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
import com.android.build.gradle.api.BaseVariantOutput
import com.android.build.gradle.api.LibraryVariant
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.DomainObjectSet
import org.gradle.api.NamedDomainObjectContainer

// Sample
private const val OUTPUT_SUFFIX_SAMPLE = ".apk"
private const val OUTPUT_NAME_SAMPLE_DEBUG = "surroundcardview-sample-debug$OUTPUT_SUFFIX_SAMPLE"
private const val OUTPUT_NAME_SAMPLE_RELEASE = "surroundcardview-sample$OUTPUT_SUFFIX_SAMPLE"

// Library
private const val OUTPUT_SUFFIX_LIBRARY = ".aar"
private const val OUTPUT_NAME_LIBRARY_DEBUG = "surroundcardview-debug$OUTPUT_SUFFIX_LIBRARY"
private const val OUTPUT_NAME_LIBRARY_RELEASE = "surroundcardview$OUTPUT_SUFFIX_LIBRARY"

internal fun DomainObjectSet<ApplicationVariant>.generateApplicationOutputs(
    namedDomainObjectContainer: NamedDomainObjectContainer<BuildType>
) {
    all {
        outputs.all { output: BaseVariantOutput ->
            val outputImpl = output as BaseVariantOutputImpl

            val fileName = name@{
                return@name when (buildType.name) {
                    namedDomainObjectContainer.getDebug -> OUTPUT_NAME_SAMPLE_DEBUG
                    namedDomainObjectContainer.getRelease -> OUTPUT_NAME_SAMPLE_RELEASE
                    else -> throw IllegalStateException("No build type found for ${buildType.name}")
                }
            }

            outputImpl.outputFileName = fileName.invoke()
            true
        }
    }
}

internal fun DomainObjectSet<LibraryVariant>.generateLibraryOutputs(
    namedDomainObjectContainer: NamedDomainObjectContainer<BuildType>
) {
    all {
        outputs.all { output: BaseVariantOutput ->
            val outputImpl = output as BaseVariantOutputImpl

            val fileName = name@{
                return@name when (buildType.name) {
                    namedDomainObjectContainer.getDebug -> OUTPUT_NAME_LIBRARY_DEBUG
                    namedDomainObjectContainer.getRelease -> OUTPUT_NAME_LIBRARY_RELEASE
                    else -> throw IllegalStateException("No build type found for ${buildType.name}")
                }
            }

            outputImpl.outputFileName = fileName.invoke()
            true
        }
    }
}
