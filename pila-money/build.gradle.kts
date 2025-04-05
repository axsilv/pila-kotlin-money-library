plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.linter)
    `maven-publish`
}

group = "pila.kotlin.money.library"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(libs.coroutines.core)

    testImplementation(libs.coroutines.test)
    testImplementation(libs.kotest)
    testImplementation(libs.kotest.runner)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["kotlin"])
            groupId = "com.pila.money"
            artifactId = "pila-money"
            version = "1.0.0"
        }
    }
}