plugins {
    alias(libs.plugins.todo.android.library)
}

android {
    namespace = "com.project.alarm"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":feature:main"))
}
