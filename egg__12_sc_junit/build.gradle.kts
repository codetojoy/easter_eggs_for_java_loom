
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOfNotNull(
        "--enable-preview",
        "--add-modules", "jdk.incubator.concurrent"
    ))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    jvmArgs("--enable-preview", "--add-modules", "jdk.incubator.concurrent")

    testLogging {
        events("passed", "skipped", "failed")
    }
}
