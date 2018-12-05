
plugins {
    id("net.ltgt.apt") version "0.10"
}

repositories {
    mavenCentral()
}

var slf4jVersion = "1.7.25"
var log4jVersion = "2.4.1"
var lombokVersion = "1.18.4"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//    implementation("org.projectlombok:lombok:$lombokVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")

//    apt("org.projectlombok:lombok:1.18.4")

    implementation("org.slf4j:jcl-over-slf4j:$slf4jVersion")
    implementation("org.slf4j:jul-to-slf4j:$slf4jVersion")
//    implementation("org.slf4j:log4j-over-slf4j:${slf4jVersion}")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
//    implementation("org.slf4j:slf4j-jdk14:${slf4jVersion}")
//    implementation("org.slf4j:slf4j-log4j12:${slf4jVersion}")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    // 用于与slf4j保持桥接
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")

    // 4.1.29.Final
    implementation("io.netty:netty-all:4.1.29.Final")
    implementation("org.jctools:jctools-core:2.1.1")
//    implementation("io.netty:netty-all:5.0.0.Alpha2")

    // JPA
    // https://mvnrepository.com/artifact/org.hibernate.javax.persistence/hibernate-jpa-2.1-api
    implementation("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")

    // protobuf
    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java
    implementation("com.google.protobuf:protobuf-java:3.6.1")

    // msgpack
    // https://mvnrepository.com/artifact/org.msgpack/msgpack
    implementation("org.msgpack:msgpack:0.6.12")

    // marshall
    implementation("org.jboss.marshalling:jboss-marshalling:1.4.10.Final")
    implementation("org.jboss.marshalling:jboss-marshalling-serial:1.4.10.Final")
    // test
    testImplementation("junit:junit:4.12")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    sourceSets {
    }
}