plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    api(project(":core"))
    compileOnly(libs.adventure.api)
}
