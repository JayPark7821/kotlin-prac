import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22" apply false
    kotlin("plugin.jpa") version "1.8.22" apply false
    kotlin("kapt") version "1.8.21"
    id("org.springframework.boot") version "3.1.5" apply false
    id("io.spring.dependency-management") version "1.1.3" apply false
}
allprojects {
    group = "kr.jay"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}


subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        runtimeOnly("com.h2database:h2")
        runtimeOnly("com.mysql:mysql-connector-j")

        implementation("org.springframework.boot:spring-boot-starter")

        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("io.micrometer:micrometer-registry-prometheus")

        kapt ("com.querydsl:querydsl-apt:5.0.0:jakarta")
        kapt ("jakarta.annotation:jakarta.annotation-api")
        kapt ("jakarta.persistence:jakarta.persistence-api")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
        withType<Test> {
            useJUnitPlatform()
        }
        named<Jar>("jar") {
            enabled = false
        }
    }
}