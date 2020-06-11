import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * This project's plugins.
 */
plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM
    kotlin("jvm") version "1.3.71" apply false
    // assciidoctor
    id("org.asciidoctor.convert") version "1.5.6" apply false
    // Apply the application to add support for building a CLI application
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

/**
 * All projects configuration.
 */
allprojects {
    group = "io.vincent.learning.stack"
    version = qualifyVersionIfNecessary(rootProject.version as String)

    apply(plugin = "idea")

    repositories {
        mavenLocal()
        jcenter()
    }
}

/**
 * This project's dependencies.
 */
dependencies {
    // Use the Kotlin JDK 8 standard library
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library
//    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

var slf4jVersion = "1.7.25"
var log4jVersion = "2.4.1"
var logbackVersion = "1.2.3"
var lombokVersion = "1.18.8"

/**
 * Sub projects configuration.
 */
configure(subprojects) {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    dependencies {
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        "implementation"("org.jetbrains.kotlin:kotlin-reflect")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit")
        "compileOnly"("org.projectlombok:lombok:$lombokVersion")
        "annotationProcessor"("org.projectlombok:lombok:$lombokVersion")

        "implementation"("org.slf4j:slf4j-api:$slf4jVersion")
        "implementation"("org.slf4j:jcl-over-slf4j:$slf4jVersion")
        "implementation"("org.slf4j:jul-to-slf4j:$slf4jVersion")
        "implementation"("ch.qos.logback:logback-classic:$logbackVersion")
        "implementation"("ch.qos.logback:logback-core:$logbackVersion")
//        "testImplementation"("junit:junit:4.12")
    }

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "1.8"
    }

    val compileJava: JavaCompile by tasks
    compileJava.sourceCompatibility = "1.8"
    compileJava.targetCompatibility = "1.8"

}

/*
 * Support publication of artifacts versioned by topic branch.
 * CI builds supply `-P BRANCH_NAME=<TOPIC>` to gradle at build time.
 * If <TOPIC> starts with 'SPR-', change version
 *     from BUILD-SNAPSHOT => <TOPIC>-SNAPSHOT
 *     e.g. 3.2.1.BUILD-SNAPSHOT => 3.2.1.SPR-1234-SNAPSHOT
 */
fun qualifyVersionIfNecessary(version: String): String {
    if (rootProject.hasProperty("BRANCH_NAME")) {
        val qualifier = rootProject.property("BRANCH_NAME") as String
        if (qualifier.startsWith("SPR-")) {
            return version.replace("BUILD", qualifier)
        }
    }
    return version
}
