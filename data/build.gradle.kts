plugins {
    alias(libs.plugins.todo.android.library)
}

android {
    namespace = "com.project.data"
}

dependencies {
    implementation(libs.androidx.room.ktx)
    implementation(libs.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    implementation(libs.gson)

}