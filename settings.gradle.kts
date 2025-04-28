/**
 * 全局插件版本管理
 */
pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings

    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/public")  // 阿里云镜像[9](@ref)
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    // plugins 版本统一
    plugins {
        id("org.springframework.boot") version springBootVersion apply false
        id("io.spring.dependency-management") version "1.1.4"
        // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.spring") version kotlinVersion apply false
        kotlin("plugin.jpa") version kotlinVersion apply false
        id("org.jetbrains.kotlin.kapt") version kotlinVersion apply false // 新增 kapt 插件
    }
}

/**
 * 全局仓库配置
 */
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        maven(url = "https://maven.aliyun.com/repository/public")  // 优先阿里云镜像[10](@ref)
        mavenCentral()
        google()
        maven(url = "https://repo.spring.io/milestone")  // Spring里程碑仓库[9](@ref)
    }
}

/**
 * Root 工程名称。
 */
rootProject.name = "learning-stack"

/**
 * 动态包含模块（避免重复代码）
 */
listOf(
    "learning-java-ecosystem:learning-java-core",
    "learning-java-ecosystem:learning-java-concurrency",
    "learning-java-ecosystem:learning-java-io-netty",
    "learning-java-ecosystem:learning-jvm",
    "learning-java-ecosystem:learning-spring",
    "learning-algorithm",
    "learning-network",
    "learning-design-pattern",
    "learning-compilation",
    "learning-operating-system",
    "learning-webserver:learning-nginx",
    "learning-webserver:learning-tomcat",
    "learning-architecture:learning-distributed-database",
    "learning-architecture:learning-distributed-id",
    "learning-architecture:learning-distributed-cache",
    "learning-architecture:learning-distributed-queue",
    "learning-architecture:learning-distributed-lock",
    "learning-architecture:learning-distributed-search",
    "learning-architecture:learning-flash-sale",
    "learning-spring:learning-spring-web-flux",
    "learning-mq",
    "learning-data-stream",
    "learning-elasticsearch",
    "learning-database:learning-mysql",
    "learning-stack-docs"
).forEach { include(it) }
//// java
//include("learning-java-ecosystem:learning-java-core")
//include("learning-java-ecosystem:learning-java-concurrency")
//include("learning-java-ecosystem:learning-java-io-netty")
//include("learning-java-ecosystem:learning-jvm")
//include("learning-java-ecosystem:learning-spring")
//// Base
//include("learning-algorithm")
//include("learning-network")
//include("learning-design-pattern")
//include("learning-compilation")
//include("learning-operating-system")
//// webserver
//include("learning-webserver:learning-nginx")
//include("learning-webserver:learning-tomcat")
//// architecture
//include("learning-architecture:learning-distributed-database")
//include("learning-architecture:learning-distributed-id")
//include("learning-architecture:learning-distributed-cache")
//include("learning-architecture:learning-distributed-queue")
//include("learning-architecture:learning-distributed-lock")
//include("learning-architecture:learning-distributed-search")
//include("learning-architecture:learning-flash-sale")
//include("learning-mq")
//
//include("learning-data-stream")
//include("learning-elasticsearch")
//// database
//include("learning-database:learning-mysql")
//
//include("learning-stack-docs")
