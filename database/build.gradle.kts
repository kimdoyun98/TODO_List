plugins {
    alias(libs.plugins.todo.android.library)
    alias(libs.plugins.todo.hilt)
}

android {
    namespace = "com.project.database"
}

dependencies {
    implementation(libs.androidx.room.ktx)
    implementation(libs.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    implementation(libs.gson)
}
