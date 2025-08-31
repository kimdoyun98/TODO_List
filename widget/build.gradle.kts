import com.project.convention.implementation

plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.todo.hilt)
}

android {
    namespace = "com.project.widget"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(projects.data)
    implementation(projects.core.ui)
    implementation(projects.core.model)
}
