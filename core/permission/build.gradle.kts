plugins {
    alias(libs.plugins.todo.android.library)
}

android {
    namespace = "com.project.permission"
}

dependencies {
    implementation(libs.tedpermission.normal)
    implementation(projects.core.ui)
}
