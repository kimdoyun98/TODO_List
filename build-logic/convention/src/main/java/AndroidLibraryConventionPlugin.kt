import com.android.build.gradle.LibraryExtension
import com.project.convention.androidTestImplementation
import com.project.convention.configureAndroid
import com.project.convention.configureKotlin
import com.project.convention.getLibrary
import com.project.convention.implementation
import com.project.convention.kapt
import com.project.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
                apply("dagger.hilt.android.plugin")
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)
            }

            configureKotlin<KotlinAndroidProjectExtension>()

            dependencies {
                implementation(libs.getLibrary("hilt.android"))
                kapt(libs.getLibrary("hilt.compiler"))
                androidTestImplementation(libs.getLibrary("hilt.android.testing"))
            }
        }
    }
}
