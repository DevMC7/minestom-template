import java.net.URI

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

val projectName: String by project
val mainClass: String by project
val group: String by project
project.group = group
val version: String by project
project.version = version

fun getLatestMinestomVersion(): String {
    val url = "https://api.github.com/repos/Minestom/Minestom/commits"
    val connection = URI.create(url).toURL().openConnection()
    connection.setRequestProperty("Accept", "application/vnd.github.v3+json")
    val response = connection.getInputStream().bufferedReader().use { it.readText() }
    val shaIndex = response.indexOf("\"sha\":\"") + 7
    return response.substring(shaIndex, shaIndex + 10)
}

fun getLatestVriVersion(): String {
    val url = "https://api.github.com/repos/Minestom/VanillaReimplementation/commits"
    val connection = URI.create(url).toURL().openConnection()
    connection.setRequestProperty("Accept", "application/vnd.github.v3+json")
    val response = connection.getInputStream().bufferedReader().use { it.readText() }
    val shaIndex = response.indexOf("\"sha\":\"") + 7
    return response.substring(shaIndex, shaIndex + 10)
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("net.minestom:minestom-snapshots:${getLatestMinestomVersion()}")
    implementation("com.github.Minestom:VanillaReimplementation:${getLatestVriVersion()}")
}

tasks.test {
    useJUnitPlatform()
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = mainClass
        }
    }
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
    }
}