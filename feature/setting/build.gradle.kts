plugins {
    alias(libs.plugins.todo.feature)
}

android {
    namespace = "com.project.feature.setting"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.androidx.preference.ktx)

    implementation(projects.data)
}
