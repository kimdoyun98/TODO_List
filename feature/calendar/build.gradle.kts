plugins {
    alias(libs.plugins.todo.feature)
}

android {
    namespace = "com.project.feature.calendar"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(projects.data)
}