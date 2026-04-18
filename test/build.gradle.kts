plugins {
    java
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

dependencies {
    implementation(project(":core"))
    implementation(libs.gson)
    implementation(libs.guava)
    implementation(libs.adventure.api)
    implementation(libs.adventure.text.serializer.gson)
}
