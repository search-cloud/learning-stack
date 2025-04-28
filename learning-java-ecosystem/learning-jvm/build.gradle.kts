
dependencies {
    implementation("org.javassist:javassist:3.30.2-GA")
    // https://mvnrepository.com/artifact/org.ow2.asm/asm
    implementation("org.ow2.asm:asm:9.8")
    implementation("org.ow2.asm:asm-commons:9.8")
    implementation("net.bytebuddy:byte-buddy-agent:1.17.5")
    // https://mvnrepository.com/artifact/org.newsclub.net/junixsocket
    implementation("org.newsclub.net:junixsocket:1.3")
    // https://mvnrepository.com/artifact/com.google.code.findbugs/annotations
    implementation("com.google.code.findbugs:annotations:3.0.0")
    // https://mvnrepository.com/artifact/com.kohlschutter.junixsocket/junixsocket-common
    implementation("com.kohlschutter.junixsocket:junixsocket-common:2.6.2")
    // https://mvnrepository.com/artifact/com.kohlschutter.junixsocket/junixsocket-native-common
    implementation("com.kohlschutter.junixsocket:junixsocket-native-common:2.6.2")
}

tasks.withType<JavaExec> {
    jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")
}
