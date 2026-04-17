plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    api(project(":calypso-core"))
    compileOnly(libs.adventure.api)
}
