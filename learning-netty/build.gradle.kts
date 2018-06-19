import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.31"

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlin_version))
    }
}

plugins {
    java
}

group = "io.vincent.learning.stack"
version = "1.0.0-SNAPSHOT"

apply {
    plugin("kotlin")
}

val kotlin_version: String by extra
val slf4j_version: String = "1.7.25"
val log4j_version: String = "2.4.1"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlin_version))

    compile("org.slf4j", "jcl-over-slf4j", "${slf4j_version}")
    compile("org.slf4j", "jul-to-slf4j", "${slf4j_version}")
//    compile("org.slf4j", "log4j-over-slf4j", "${slf4j_version}")
    compile("org.slf4j", "slf4j-api", "${slf4j_version}")
//    compile("org.slf4j", "slf4j-jdk14", "${slf4j_version}")
//    compile("org.slf4j", "slf4j-log4j12", "${slf4j_version}")
    compile("org.apache.logging.log4j", "log4j-api", "${log4j_version}")
    compile("org.apache.logging.log4j", "log4j-core", "${log4j_version}")
    // 用于与slf4j保持桥接
    compile("org.apache.logging.log4j", "log4j-slf4j-impl", "${log4j_version}")

    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}