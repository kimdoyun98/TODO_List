plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.todo.hilt)
    alias(libs.plugins.todo.room)
}

android {
    namespace = "com.project.database"
}

dependencies {
    implementation(libs.gson)

    api(projects.core.model)
}
