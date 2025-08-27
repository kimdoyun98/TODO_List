plugins {
    alias(libs.plugins.todo.android.library)
}

android {
    namespace = "com.project.worker"
}

dependencies {
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)

    implementation(projects.core.ui)
    implementation(projects.core.alarm)
    implementation(projects.data)
}
