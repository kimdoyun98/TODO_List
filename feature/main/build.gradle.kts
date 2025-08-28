plugins {
    alias(libs.plugins.todo.feature)
}

android {
    namespace = "com.project.feature.main"

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    //implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(projects.data)
    implementation(projects.core.model)

    implementation(projects.feature.home)
    implementation(projects.feature.routine)
    implementation(projects.feature.schedule)
    implementation(projects.feature.calendar)
    implementation(projects.feature.setting)
}
