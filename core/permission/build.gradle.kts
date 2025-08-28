plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.todo.hilt)
}

android {
    namespace = "com.project.permission"
}

dependencies {
    implementation(libs.tedpermission.normal)
    implementation(projects.core.ui)
}
