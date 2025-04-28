
dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // add servlet dependency
    implementation("javax.servlet:javax.servlet-api:3.1.0")
    implementation("com.google.guava:guava:32.0.1-android")
    implementation("org.springframework.boot:spring-boot-starter-web")
//    testImplementation("junit:junit:4.13.1")
//    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
//    testImplementation("org.assertj:assertj-core:3.11.1")
//    testImplementation("org.mockito:mockito-core:2.23.4")
    testImplementation(kotlin("test"))
}