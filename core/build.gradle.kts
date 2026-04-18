plugins {
    java
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

dependencies {
    compileOnly(libs.gson)
    compileOnly(libs.guava)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.text.serializer.gson)
    compileOnly(libs.jetbrains.annotations)
}
