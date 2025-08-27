plugins {
    alias(libs.plugins.todo.android.library)
}

android {
    namespace = "com.project.core.ui"

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(projects.data)
    implementation(libs.androidx.appcompat.resources)

    implementation(libs.material)
}
