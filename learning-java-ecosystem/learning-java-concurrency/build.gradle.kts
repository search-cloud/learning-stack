plugins {
    id("org.springframework.boot") version "2.1.3.RELEASE"
}

apply(plugin="io.spring.dependency-management")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:23.0")
    implementation("org.projectlombok:lombok")
    testImplementation("junit:junit:4.12")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
