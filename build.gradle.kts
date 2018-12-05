
/**
 * This project's plugins.
 */
plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM
    kotlin("jvm") version "1.3.10" apply false
    // assciidoctor
    id("org.asciidoctor.convert") version "1.5.6" apply false
    // Apply the application to add support for building a CLI application
}

/**
 * All projects configuration.
 */
allprojects {
    group = "io.vincent.learning.stack"
    version = 1.0
//    rootProject.version = qualifyVersionIfNecessary(rootProject.version)

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
        "testImplementation"("junit:junit:4.12")
    }
}

/*
 * Support publication of artifacts versioned by topic branch.
 * CI builds supply `-P BRANCH_NAME=<TOPIC>` to gradle at build time.
 * If <TOPIC> starts with 'SPR-', change version
 *     from BUILD-SNAPSHOT => <TOPIC>-SNAPSHOT
 *     e.g. 3.2.1.BUILD-SNAPSHOT => 3.2.1.SPR-1234-SNAPSHOT
 */

fun qualifyVersionIfNecessary(version: java.lang.String): java.lang.String {
    if (rootProject.hasProperty("BRANCH_NAME")) {
        val qualifier = rootProject.property("BRANCH_NAME") as java.lang.String
        if (qualifier.startsWith("SPR-")) {
            return version.replace("BUILD", qualifier) as java.lang.String
        }
    }
    return version
}