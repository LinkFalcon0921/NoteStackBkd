plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.flintcore"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    val JITPACK_REPO = "https://jitpack.io"

    // Locals
    mavenLocal()

    // Remote
    mavenCentral()
    google()
    maven(JITPACK_REPO)

}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // Web client
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("com.auth0:java-jwt:4.4.0")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Map struct Start
    implementation("org.mapstruct:mapstruct:1.6.3")

    // Map struct lombok binding
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    runtimeOnly("org.postgresql:postgresql")
    testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    // Map struct End

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // test implementation
    testImplementation("org.mockito:mockito-core:5.16.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    // DB just for runtime
    runtimeOnly("com.h2database:h2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
