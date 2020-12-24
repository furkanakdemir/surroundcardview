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
package utils

import org.gradle.api.Project
import java.util.Locale

internal fun isLinuxOrMacOs(): Boolean {
    val osName = System.getProperty("os.name").toLowerCase(Locale.ROOT)
    return listOf("linux", "mac os", "macos").contains(osName)
}

internal inline val parallelForks: Int
    get() = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1

/**
 * Usage: <code>./gradlew build -PwarningsAsErrors=true</code>.
 */
internal fun shouldTreatCompilerWarningsAsErrors(project: Project): Boolean {
    return project.findProperty("warningsAsErrors") == "true"
}
