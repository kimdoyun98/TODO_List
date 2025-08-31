import com.android.build.gradle.TestExtension
import com.project.convention.androidTestImplementation
import com.project.convention.configureAndroid
import com.project.convention.getLibrary
import com.project.convention.libs
import com.project.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<TestExtension> {
                configureAndroid(this)
            }

            dependencies {
                testImplementation(libs.getLibrary("junit"))
                androidTestImplementation(libs.getLibrary("androidx.junit"))
                androidTestImplementation(libs.getLibrary("androidx.espresso.core"))
            }
        }
    }
}