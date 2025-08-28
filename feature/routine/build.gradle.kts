import com.project.convention.implementation

plugins {
    alias(libs.plugins.todo.feature)
}

android {
    namespace = "com.project.feature.routine"

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(projects.data)
    implementation(projects.core.alarm)
    implementation(projects.core.permission)
    implementation(projects.core.model)
}
