import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


java.sourceCompatibility = JavaVersion.VERSION_17
dependencies {
    implementation(project(":coupon-core"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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
        getByName<Jar>("jar") {
            enabled = false
        }
    }
}