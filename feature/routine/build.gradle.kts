import com.project.convention.implementation

plugins {
    alias(libs.plugins.todo.android.library)
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

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(projects.core.ui)
    implementation(projects.core.navigation)
    implementation(projects.data)
    implementation(projects.core.alarm)
    implementation(projects.core.permission)
}
