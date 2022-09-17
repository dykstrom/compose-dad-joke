
import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
}

group = "se.dykstrom.compose.dadjoke"
version = "0.1.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.6.10")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}
