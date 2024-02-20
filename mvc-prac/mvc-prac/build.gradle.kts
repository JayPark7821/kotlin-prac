plugins {
    kotlin("jvm") version "1.9.21"
}

group = "kr.jay"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.tomcat.embed:tomcat-embed-core:8.5.42")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:8.5.42")

    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet:jstl:1.2")

    implementation("io.github.microutils:kotlin-logging:3.0.5")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}