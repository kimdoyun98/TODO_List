plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.todo.hilt)
}

android {
    namespace = "com.project.alarm"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.data)
}
