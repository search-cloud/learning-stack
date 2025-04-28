import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // https://mvnrepository.com/artifact/com.rabbitmq/amqp-client
    implementation("com.rabbitmq:amqp-client:5.6.0")
//    testImplementation("junit:junit:4.13.1")
}

configure(subprojects) {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    dependencies {
//        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//        "implementation"("org.jetbrains.kotlin:kotlin-reflect")
//        "testImplementation"("org.jetbrains.kotlin:kotlin-test")
//        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit")
        implementation("com.rabbitmq:amqp-client:5.6.0")
//        testImplementation("junit:junit:4.13.1")
    }

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "17"
    }

    val compileJava: JavaCompile by tasks
    compileJava.sourceCompatibility = "17"
    compileJava.targetCompatibility = "17"

}
