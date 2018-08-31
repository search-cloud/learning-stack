import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.2.51"

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
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

val kotlinVersion: String by extra
val slf4jVersion: String = "1.7.25"
val log4jVersion: String = "2.4.1"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlinVersion))

    compile("org.slf4j", "jcl-over-slf4j", slf4jVersion)
    compile("org.slf4j", "jul-to-slf4j", slf4jVersion)
//    compile("org.slf4j", "log4j-over-slf4j", "${slf4jVersion}")
    compile("org.slf4j", "slf4j-api", slf4jVersion)
//    compile("org.slf4j", "slf4j-jdk14", "${slf4jVersion}")
//    compile("org.slf4j", "slf4j-log4j12", "${slf4jVersion}")
    compile("org.apache.logging.log4j", "log4j-api", log4jVersion)
    compile("org.apache.logging.log4j", "log4j-core", log4jVersion)
    // 用于与slf4j保持桥接
    compile("org.apache.logging.log4j", "log4j-slf4j-impl", log4jVersion)

    // 4.1.25.Final
    compile("io.netty","netty-all", "5.0.0.Alpha2")

    // JPA
    // https://mvnrepository.com/artifact/org.hibernate.javax.persistence/hibernate-jpa-2.1-api
    compile("org.hibernate.javax.persistence", "hibernate-jpa-2.1-api", "1.0.2.Final")

    // protobuf
    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java
    compile("com.google.protobuf", "protobuf-java", "3.6.1")

    // msgpack
    // https://mvnrepository.com/artifact/org.msgpack/msgpack
    compile("org.msgpack", "msgpack", "0.6.12")

    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}