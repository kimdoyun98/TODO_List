import com.project.convention.implementation
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.todo.feature)
}

android {
    namespace = "com.project.feature.home"

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.mpandroidchart)

    implementation(projects.data)
    implementation(projects.widget)
    implementation(projects.core.model)
}
