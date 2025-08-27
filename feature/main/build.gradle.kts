plugins {
    alias(libs.plugins.todo.android.library)
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

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //implementation(libs.androidx.navigation.runtime)

    implementation(projects.core.navigation)
    implementation(projects.core.ui)

    implementation(projects.feature.home)
    implementation(projects.feature.routine)
    implementation(projects.feature.schedule)
    implementation(projects.feature.calendar)
    implementation(projects.feature.setting)
}
