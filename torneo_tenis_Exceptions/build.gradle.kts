plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.lighthousegames:logging:1.3.0")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    implementation("org.mybatis:mybatis:3.5.13")
    implementation("org.xerial:sqlite-jdbc:3.45.2.0")

    implementation("com.michael-bull.kotlin-result:kotlin-result:2.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("io.github.pdvrieze.xmlutil:core-jvm:0.86.3")
    implementation("io.github.pdvrieze.xmlutil:serialization-jvm:0.86.3")

    implementation("com.github.ajalt.mordant:mordant:2.0.0-beta9")

    testImplementation("io.mockk:mockk:1.13.10")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}