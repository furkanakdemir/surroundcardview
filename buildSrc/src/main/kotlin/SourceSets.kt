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
import InternalSourceSet.ANDROID_TEST
import InternalSourceSet.MAIN
import InternalSourceSet.TEST
import com.android.build.api.dsl.AndroidSourceSet
import org.gradle.api.NamedDomainObjectContainer

private object InternalSourceSet {
    const val MAIN = "main"
    const val TEST = "test"
    const val ANDROID_TEST = "androidTest"
}

fun NamedDomainObjectContainer<out AndroidSourceSet>.createMainSourceSet() {
    getByName(MAIN) {
        java.srcDirs("src/main/kotlin", "src/main/java")
    }
}

fun NamedDomainObjectContainer<out AndroidSourceSet>.createTestSourceSet() {
    getByName(TEST) {
        java.srcDirs("src/test/kotlin", "src/commonTest/java")
        resources.srcDir("src/test/resources")
        assets.srcDir("src/test/assets")
    }
}

fun NamedDomainObjectContainer<out AndroidSourceSet>.createAndroidTestSourceSet() {
    getByName(ANDROID_TEST) {
        java.srcDirs("src/androidTest/kotlin", "src/androidTest/kotlin")
        resources.srcDir("src/androidTest/resources")
        assets.srcDir("src/androidTest/assets")
    }
}
