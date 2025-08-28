import com.project.convention.getLibrary
import com.project.convention.implementation
import com.project.convention.kapt
import com.project.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-kapt")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                implementation(libs.getLibrary("dagger.hilt.android"))
                kapt(libs.getLibrary("hilt.compiler"))
            }
        }
    }
}
