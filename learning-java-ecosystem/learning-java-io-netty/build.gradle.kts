import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//    id("net.ltgt.apt") version "0.10"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    compileOnly("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")

//    apt("org.projectlombok:lombok:1.18.4")

    // 4.1.29.Final
    implementation("io.netty:netty-all:4.1.94.Final")
    implementation("org.jctools:jctools-core:2.1.1")
//    implementation("io.netty:netty-all:5.0.0.Alpha2")

    // JPA
    // https://mvnrepository.com/artifact/org.hibernate.javax.persistence/hibernate-jpa-2.1-api
    implementation("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")

    // protobuf
    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java
    implementation("com.google.protobuf:protobuf-java:3.25.1")

    // msgpack
    // https://mvnrepository.com/artifact/org.msgpack/msgpack
    implementation("org.msgpack:msgpack:0.6.12")

    // marshall
    implementation("org.jboss.marshalling:jboss-marshalling:2.0.11.Final")
    implementation("org.jboss.marshalling:jboss-marshalling-serial:2.0.11.Final")
}
