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
import com.jfrog.bintray.gradle.BintrayExtension
import ext.applyDefaultTestConfigs
import ext.createAndroidTest
import ext.createDebug
import ext.createMain
import ext.createRelease
import ext.createTest
import ext.generateLibraryOutputs
import ext.setDefaults
import java.io.FileInputStream
import java.util.Date
import java.util.Properties

plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
    `maven-publish`
    id(Plugins.bintray).version(PluginVersions.bintray)
}

android {
    compileSdkVersion(AndroidSdk.sdk_compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.sdk_minimum)
        targetSdkVersion(AndroidSdk.sdk_target)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        createDebug(this, true)
        createRelease(this, signingConfigs, true)
        generateLibraryOutputs(libraryVariants)
    }

    sourceSets {
        createMain(this)
        createTest(this)
        createAndroidTest(this)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions.applyDefaultTestConfigs()

    lintOptions.setDefaults()

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

val bintrayUser: String by lazy { properties.getProperty("bintrayUser") }
val bintrayKey: String by lazy { properties.getProperty("bintrayKey") }

project.afterEvaluate {

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

    publishing {
        publications {
            create<MavenPublication>(publicationName) {
                groupId = group
                artifactId = pomArtifactId
                version = versionName
                artifact("$buildDir/outputs/aar/surroundcardview.aar")
                artifact(androidSourcesJar.get())
                pom {
                    name.set(pomName)
                    description.set(pomDescription)
                    url.set(pomUrl)
                    packaging = pomPackaging
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set(pomDeveloperId)
                            name.set(pomDeveloperName)
                        }
                    }

                    scm {
                        url.set(pomUrl)
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

    bintray {

        user = bintrayUser
        key = bintrayKey
        publish = true
        setPublications(publicationName)
        pkg(
            delegateClosureOf<BintrayExtension.PackageConfig> {
                repo = "surroundcardview"
                name = "com.furkanakdemir.surroundcardview"
                websiteUrl = pomUrl
                githubRepo = "furkanakdemir/surroundcardview"
                vcsUrl = pomScmUrl
                description = pomDescription
                setLabels("kotlin")
                setLicenses("Apache-2.0")
                desc = description

                // Configure version
                version.apply {
                    name = versionName
                    released = Date().toString()
                }
            }
        )
    }
}
