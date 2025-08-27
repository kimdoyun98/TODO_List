plugins {
    alias(libs.plugins.todo.android.library)
}

android {
    namespace = "com.project.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.ui.ktx)
}
