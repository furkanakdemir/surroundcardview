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
import com.android.build.gradle.api.AndroidSourceSet
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

private object InternalSourceSet {
    const val MAIN = "main"
    const val TEST = "test"
    const val ANDROID_TEST = "androidTest"
}

internal interface SourceSetCreator {
    val name: String

    fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>,
        project: Project
    ): AndroidSourceSet
}

internal object Main : SourceSetCreator {
    override val name = InternalSourceSet.MAIN

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>,
        project: Project
    ): AndroidSourceSet {
        return namedDomainObjectContainer.maybeCreate(name).apply {
            java.srcDir("src/main/kotlin")
        }
    }
}

internal object Test : SourceSetCreator {
    override val name = InternalSourceSet.TEST

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>,
        project: Project
    ): AndroidSourceSet {
        return namedDomainObjectContainer.maybeCreate(name).apply {
            java.srcDirs("src/test/kotlin", "src/commonTest/java")
            resources.srcDir("src/test/resources")
            assets.srcDir("src/test/assets")
        }
    }
}

internal object AndroidTest : SourceSetCreator {
    override val name = InternalSourceSet.ANDROID_TEST

    override fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<AndroidSourceSet>,
        project: Project
    ): AndroidSourceSet {
        return namedDomainObjectContainer.maybeCreate(name).apply {
            java.srcDirs("src/androidTest/kotlin", "src/androidTest/kotlin")
            resources.srcDir("src/androidTest/resources")
            assets.srcDir("src/androidTest/assets")
        }
    }
}
