plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

subprojects {
    group = "kr.moonshine"
    version = "0.0.1"
}

allprojects {
    repositories {
        mavenCentral()
    }
}
