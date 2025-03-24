plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.linter)
    `java-library`
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    testImplementation(libs.kotest)
}
