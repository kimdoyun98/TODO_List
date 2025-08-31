plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.todo.hilt)
}

android {
    namespace = "com.project.data"
}

dependencies {
    implementation(projects.database)
}
