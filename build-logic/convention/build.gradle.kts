plugins {
    `kotlin-dsl`
}

group = "com.project.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        plugins {
            register("AndroidApplication") {
                id = "todo.android.application"
                implementationClass = "AndroidApplicationConventionPlugin"
            }

            register("AndroidLibrary") {
                id = "todo.android.library"
                implementationClass = "AndroidLibraryConventionPlugin"
            }

            register("AndroidTest") {
                id = "todo.android.test"
                implementationClass = "AndroidTestConventionPlugin"
            }

            register("Hilt") {
                id = "todo.hilt"
                implementationClass = "HiltPlugin"
            }

            register("Feature") {
                id = "todo.feature"
                implementationClass = "FeatureConventionPlugin"
            }
        }
    }
}
