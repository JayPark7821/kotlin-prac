plugins {
    kotlin("jvm")
}

group = "kr.jay"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.4.8")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("io.projectreactor:reactor-core:3.5.7")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}