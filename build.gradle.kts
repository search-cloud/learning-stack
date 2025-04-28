import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// 全局版本变量
val kotlinVersion = "1.9.22"
val springBootVersion = "3.2.2"
val lombokVersion = "1.18.38"
val slf4jVersion = "2.0.11"      // 同步升级 SLF4J 至 2.x
val logbackVersion = "1.4.14"    // 建议升级到 1.4.14 (最新稳定版)
val log4jVersion = "2.4.1"

/**
 * This project's plugins.
 * 根项目特殊配置（如全局插件）
 */
plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM
    kotlin("jvm")
    // Spring Boot
    id("org.springframework.boot") apply false
    // Apply the application to add support for building a CLI application
    id("io.spring.dependency-management")
    kotlin("plugin.spring") apply false
    kotlin("plugin.jpa") apply false
//    id("org.jetbrains.kotlin.kapt")  // 新增 kapt 插件
    // assciidoctor
    id("org.asciidoctor.convert") version "1.5.6"  apply false
}

/**
 * All projects configuration.
 */
allprojects {
    group = "io.vincent.learning.stack"
//    version = qualifyVersionIfNecessary(rootProject.version as String)

    apply(plugin = "idea")
    
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
        options.annotationProcessorPath = configurations.getByName("annotationProcessor")
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
 * Subprojects configuration.
 * 子模块通用配置
 */
configure(subprojects) {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "io.spring.dependency-management")
//    apply(plugin = "org.jetbrains.kotlin.kapt")  // Lombok注解处理[7](@ref)

    dependencies {

//        compileOnly("org.projectlombok:lombok:$lombokVersion")
//        annotationProcessor("org.projectlombok:lombok:$lombokVersion")
//        kapt("org.projectlombok:lombok:$lombokVersion")
//        testCompileOnly("org.projectlombok:lombok:$lombokVersion")
//        testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

        implementation("org.slf4j:slf4j-api:$slf4jVersion")
        implementation("org.slf4j:jcl-over-slf4j:$slf4jVersion")
        implementation("org.slf4j:jul-to-slf4j:$slf4jVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")
        implementation("ch.qos.logback:logback-core:$logbackVersion")

        //        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//        "implementation"("org.jetbrains.kotlin:kotlin-reflect")
//        "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
        testImplementation("org.jetbrains.kotlin:kotlin-test")
//        testImplementation("junit:junit:4.12")
//        testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
        testImplementation("org.assertj:assertj-core:3.11.1")
        testImplementation("org.mockito:mockito-core:2.23.4")
    }

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "17"
    }

    val compileJava: JavaCompile by tasks
    compileJava.sourceCompatibility = "17"
    compileJava.targetCompatibility = "17"

}

//
subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    // 公共配置
    apply(plugin = "idea")
    apply(plugin = "io.spring.dependency-management")
//    apply(plugin = "org.jetbrains.kotlin.kapt")  // Lombok注解处理[7](@ref)

    // 依赖管理
    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:${property("springBootVersion")}")
        }
    }

    // 判断是否为纯 Java 模块
//    val isJavaProject = project.name.contains("java")
//    val isKotlinProject = project.name.contains("kotlin")

    // 根据模块类型应用插件
//    if (isJavaProject) {
//        apply(plugin = "java")
//    } else {
//        apply(plugin = "org.jetbrains.kotlin.jvm")
//        // 统一JDK版本
//        kotlin {
//            jvmToolchain(17)  // Spring Boot 3.x要求JDK17[3](@ref)
//        }
//    }
    
    // 仅 Java 模块启用 Lombok
//    if (isJavaProject) {
//        apply(plugin = "org.jetbrains.kotlin.kapt")
        configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

//        dependencies {
//            // 重点修复：Java 模块必须使用 annotationProcessor 而非 kapt
//            compileOnly("org.projectlombok:lombok:$lombokVersion")
//            annotationProcessor("org.projectlombok:lombok:$lombokVersion")
//            testCompileOnly("org.projectlombok:lombok:$lombokVersion")
//            testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
//        }
//    }

    // 仅 Kotlin 模块配置 kapt
//    if (isKotlinProject) {
//        apply(plugin = "org.jetbrains.kotlin.kapt")
//        dependencies {
//            kapt("org.projectlombok:lombok:$lombokVersion")
//        }
//    }

    dependencies {
        // Lombok
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")  // 替代annotationProcessor[8](@ref)
        testCompileOnly("org.projectlombok:lombok")
        testAnnotationProcessor("org.projectlombok:lombok")

        // 日志
        implementation("ch.qos.logback:logback-classic")  // 升级至安全版本[6](@ref)

        // Kotlin基础库
        implementation(kotlin("stdlib-jdk8")) // 兼容JDK8的标准库
        implementation("org.jetbrains.kotlin:kotlin-reflect")
    }

    // 编译选项
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict")
            incremental = true  // 增量编译优化[5](@ref)
        }
    }

    sourceSets {
        main {
            java.srcDirs("src/main/java")
            kotlin.srcDirs("src/main/kotlin")
        }
        test {
            // 测试目录类似
            java.srcDirs("src/test/java")
            kotlin.srcDirs("src/test/kotlin")
        }
    }

    tasks.test {
        useJUnitPlatform()
        jvmArgs = listOf(
            // For java.io access
            "--add-opens=java.base/java.io=ALL-UNNAMED",
            // For Netty's internal reflection requirements
            "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED",
            // For JBoss Marshalling reflection
            "--add-opens=java.base/jdk.internal.reflect=ALL-UNNAMED",
            // For Netty's NIO operations
            "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
            // Export reflection module
            "--add-exports=java.base/jdk.internal.reflect=ALL-UNNAMED"
        )
    }

}
