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
import com.android.build.gradle.internal.dsl.SigningConfig
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

object SigningFactory {
    fun create(
        project: Project,
        namedDomainObjectContainer: NamedDomainObjectContainer<SigningConfig>
    ): SigningConfig {
        return namedDomainObjectContainer.maybeCreate(InternalBuildType.RELEASE).apply {
            storeFile = project.file("scv.jks")
            storePassword = System.getenv("scvStorePassword")
            keyAlias = System.getenv("scvStoreKeyAlias")
            keyPassword = System.getenv("scvStorePasswordKeyPassword")
        }
    }
}
