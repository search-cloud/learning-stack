//plugins {
//    id("org.springframework.boot")
//}

apply(plugin="org.springframework.boot")
apply(plugin="io.spring.dependency-management")
//val springBootVersion = "2.1.3.RELEASE"
//val jacksonVersion = "2.9.8"
//var lombokVersion = "1.18.8"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:32.0.1-android")
//    implementation("org.projectlombok:lombok")
//    testImplementation("junit:junit:4.12")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}
