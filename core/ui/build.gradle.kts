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
    implementation(libs.androidx.appcompat.resources)
    implementation(libs.material)

    implementation(projects.data)
}
