plugins {
    java
    `maven-publish`
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.123-stable") // Latest Paper API
}

tasks {
    jar {
        archiveBaseName.set(rootProject.name + "-" + project.name)
        archiveClassifier.set("")
        destinationDirectory.set(file("$rootDir/build/libs"))
    }

    register<Javadoc>("generateJavadoc") {
        source = sourceSets.main.get().allJava
        classpath += project.configurations.getByName("compileClasspath").asFileTree
        title = "Konquest ${project.version} Documentation"
        options.overview("overview.html")
        setDestinationDir(file("$rootDir/docs"))
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }

    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = rootProject.name + "-" + project.name
            version = project.version.toString()
        }
    }
    repositories {
        mavenLocal()
    }
}
