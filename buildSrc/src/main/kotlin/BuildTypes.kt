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
import InternalBuildType.DEBUG
import InternalBuildType.RELEASE
import com.android.build.gradle.ProguardFiles.ProguardFile
import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.SigningConfig
import ext.gitSha
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

object InternalBuildType {
    const val RELEASE = "release"
    const val DEBUG = "debug"
}

object BuildTypeFactory {

    fun createDebug(
        project: Project,
        buildTypeContainer: NamedDomainObjectContainer<BuildType>,
        isLibrary: Boolean = false
    ): BuildType {
        return buildTypeContainer.maybeCreate(DEBUG).apply {
            if (isLibrary) {
                versionNameSuffix = "-dev-${project.gitSha}"
            } else {
                applicationIdSuffix = ".dev"
            }
            isTestCoverageEnabled = true
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    fun createRelease(
        project: Project,
        buildTypeContainer: NamedDomainObjectContainer<BuildType>,
        signingContainer: NamedDomainObjectContainer<SigningConfig>,
        isLibrary: Boolean = false
    ): BuildType {
        return buildTypeContainer.maybeCreate(RELEASE).apply {
            isTestCoverageEnabled = false
            isMinifyEnabled = true

            isDebuggable = false

            val defaultProguardFile = getDefaultProguardFile(
                ProguardFile.OPTIMIZE.fileName,
                project.layout.buildDirectory
            )
            if (!isLibrary) {
                isShrinkResources = true
                signingConfig = signingContainer.getByName(RELEASE)
                proguardFiles(defaultProguardFile, "proguard-rules.pro")
            } else {
                consumerProguardFiles("consumer-rules.pro")
            }
        }
    }
}
