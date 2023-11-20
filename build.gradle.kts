plugins {
    id("java")
}

group = "com.willfp"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

java {
    manifest {
        attributes["Main-Class"] = "com.willfp.ecm2418.CardGame"
    }
}