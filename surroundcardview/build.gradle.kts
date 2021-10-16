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
import ext.applyDefault
import java.io.FileInputStream
import java.util.Properties

plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    `maven-publish`
    signing
}

android {
    compileSdk = AndroidSdk.compileVersion

    defaultConfig {
        minSdk = AndroidSdk.minimumVersion
        targetSdk = AndroidSdk.targetVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        consumerProguardFiles("proguard-rules.pro")
    }

    buildFeatures {
        buildConfig = false
    }

    sourceSets {
        createMainSourceSet()
        createTestSourceSet()
        createAndroidTestSourceSet()
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    kotlinOptions.applyDefault()

    testOptions.applyDefault()

    lintOptions.applyDefault(rootProject.file("qa/lint/lint.xml"))

    resourcePrefix("scv_")
}

dependencies {
    implementation(Deps.kotlin)
    implementation(Deps.material)
    implementation(Deps.core_ktx)

    testImplementation(TestDeps.junit)
}

val properties = Properties()
val fis = FileInputStream(rootProject.file("local.properties"))
properties.load(fis)

val publicationName = "surroundcardview"

val androidSourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

val signingKeyId: String by lazy { properties.getProperty("signing.keyId") }
val signingKey: String by lazy { properties.getProperty("signing.key") }
val signingPassword: String by lazy { properties.getProperty("signing.password") }

val pomArtifactId: String by project
val pomName: String by project
val pomPackaging: String by project
val pomDescription: String by project
val pomUrl: String by project
val pomScmUrl: String by project
val pomScmConnection: String by project
val pomScmDevConnection: String by project
val pomLicenceName: String by project
val pomLicenceUrl: String by project
val pomLicenceDist: String by project
val pomDeveloperId: String by project
val pomDeveloperName: String by project

val group: String by project
val versionName: String by project

val depGroupId = "groupId"
val depArtifactId = "artifactId"
val depVersion = "version"
val depTagParent = "dependencies"
val depTagNode = "dependency"

val artifactPath = "$buildDir/outputs/aar/surroundcardview-release.aar"

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            groupId = group
            artifactId = pomArtifactId
            version = versionName
            artifact(artifactPath)
            artifact(androidSourcesJar.get())
            pom {
                name.set(pomName)
                description.set(pomDescription)
                url.set(pomUrl)
                packaging = pomPackaging
                licenses {
                    license {
                        name.set(pomLicenceName)
                        url.set(pomLicenceUrl)
                        distribution.set(pomLicenceDist)
                    }
                }
                developers {
                    developer {
                        id.set(pomDeveloperId)
                        name.set(pomDeveloperName)
                    }
                }
                scm {
                    url.set(pomScmUrl)
                    connection.set(pomScmConnection)
                    developerConnection.set(pomScmDevConnection)
                }

                withXml {
                    val dependenciesNode = asNode().appendNode(depTagParent)
                    configurations.implementation.get().allDependencies.forEach {
                        val dependencyNode = dependenciesNode.appendNode(depTagNode)
                        dependencyNode.apply {
                            appendNode(depGroupId, it.group)
                            appendNode(depArtifactId, it.name)
                            appendNode(depVersion, it.version)
                        }
                    }
                }
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications)
}
