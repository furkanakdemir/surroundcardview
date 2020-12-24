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
package plugins

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import utils.javaVersion

subprojects {

    apply<DetektPlugin>()

    configure<DetektExtension> {

        config = files("$rootDir/qa/detekt/default-detekt-config.yml")
        baseline = file("$rootDir/qa/detekt/baseline.xml")
        failFast = true
        buildUponDefaultConfig = true
        parallel = true
        autoCorrect = true

        reports {
            xml {
                enabled = true
                destination = file("${project.buildDir}/reports/detekt/detekt.xml")
            }

            html {
                enabled = true
                destination = file("${project.buildDir}/reports/detekt/detekt.html")
            }
            txt {
                enabled = false
            }
        }
    }

    tasks {
        withType<Detekt> {
            include("**/*.kt")
            include("**/*.kts")
            exclude("resources/")
            exclude(".*build.*")
            exclude(".*/tmp/.*")

            jvmTarget = javaVersion.toString()
        }
    }
}


val detektFormat by tasks.registering(Detekt::class) {
    description = "Reformats whole code base."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(files(project.projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    config.setFrom(files("${project.rootDir}/qa/detekt/format.yml"))
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektAll by tasks.registering(Detekt::class) {
    description = "Runs over whole code base without the starting overhead for each module."
    parallel = true
    buildUponDefaultConfig = true
    setSource(files(project.projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    baseline.set(file("${project.rootDir}/qa/detekt/baseline.xml"))
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}
