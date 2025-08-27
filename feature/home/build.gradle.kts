import com.project.convention.implementation
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.todo.android.library)
}

android {
    namespace = "com.project.feature.home"

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.mpandroidchart)

    implementation(projects.core.ui)
    implementation(projects.core.navigation)
    implementation(projects.data)
    implementation(projects.widget)
}
