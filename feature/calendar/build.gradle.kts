plugins {
    alias(libs.plugins.todo.android.library)
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

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(projects.core.ui)
    implementation(projects.core.navigation)
    implementation(projects.data)
}