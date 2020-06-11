import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("junit:junit:4.12")
}

configure(subprojects) {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    dependencies {
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        "implementation"("org.jetbrains.kotlin:kotlin-reflect")
//        "implementation"("org.spring:kotlin-reflect")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit")
    }

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "1.8"
    }

    val compileJava: JavaCompile by tasks
    compileJava.sourceCompatibility = "1.8"
    compileJava.targetCompatibility = "1.8"

}
