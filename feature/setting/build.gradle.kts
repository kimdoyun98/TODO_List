plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.kotlin.android)
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

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(projects.core.ui)
    implementation(projects.core.navigation)
    implementation(projects.data)
}
