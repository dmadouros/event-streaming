plugins {
    application
    kotlin("jvm") version "1.6.21"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

repositories {
    jcenter()
}

version = "0.2.0"

application {
    mainClass.set("nile.StreamAppKt")
}

dependencies {
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.17.2"))
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:29.0-jre")
    implementation("org.apache.kafka:kafka-clients:2.0.0")
    implementation("com.maxmind.geoip2:geoip2:2.12.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.13.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    implementation("org.apache.logging.log4j:log4j-api")
    implementation("org.apache.logging.log4j:log4j-core")
    implementation("org.apache.logging.log4j:log4j-slf4j18-impl")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

//    api("org.apache.commons:commons-math3:3.6.1")
}
