import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("junit:junit")
}

configure(subprojects) {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    dependencies {
//        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//        "implementation"("org.jetbrains.kotlin:kotlin-reflect")
//        "implementation"("org.spring:kotlin-reflect")
//        "testImplementation"("org.jetbrains.kotlin:kotlin-test")
//        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit")
        compileOnly("org.projectlombok:lombok")
        testCompileOnly("org.projectlombok:lombok")
    }

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "17"
    }

    val compileJava: JavaCompile by tasks
    compileJava.sourceCompatibility = "17"
    compileJava.targetCompatibility = "17"

}
