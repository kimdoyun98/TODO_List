import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.project.convention.configureAndroid
import com.project.convention.getLibrary
import com.project.convention.implementation
import com.project.convention.kapt
import com.project.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
                apply("dagger.hilt.android.plugin")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureAndroid(commonExtension = this)
            }

            dependencies {
                implementation(libs.getLibrary("dagger.hilt.android"))
                kapt(libs.getLibrary("hilt.compiler"))
            }
        }
    }
}
