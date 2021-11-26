group = "io.github.pulsebeat02"
version = "v1.4.0"
description = "Java archiving library"

plugins {
    java
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("org.apache.commons:commons-compress:1.21")
    implementation("org.tukaani:xz:1.8")
    testImplementation("junit:junit:4.13.1")
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
    }
}

tasks {
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    publish {
        dependsOn(clean)
        dependsOn(build)
    }
}


publishing {
    repositories {
        maven {
            setUrl("https://pulsebeat02.jfrog.io/artifactory/pulse-gradle-release-local")
            credentials {
                username = ""
                password = ""
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
