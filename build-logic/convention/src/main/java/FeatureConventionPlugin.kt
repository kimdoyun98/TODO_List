import com.android.build.gradle.LibraryExtension
import com.project.convention.configureAndroid
import com.project.convention.configureKotlin
import com.project.convention.getLibrary
import com.project.convention.implementation
import com.project.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("todo.android.library")
                apply("todo.hilt")
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)

                buildFeatures {
                    viewBinding = true
                }
            }

            configureKotlin<KotlinAndroidProjectExtension>()

            dependencies {
                implementation(project(":core:ui"))
                implementation(project(":core:navigation"))

                implementation(libs.getLibrary("androidx.navigation.fragment.ktx"))
                implementation(libs.getLibrary("androidx.navigation.ui.ktx"))
            }
        }
    }
}
