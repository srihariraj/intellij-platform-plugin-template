plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.3"
    kotlin("jvm") version "1.9.24"
}

group = "com.github.srihariraj"
version = "1.0.0-init"

repositories {
    mavenCentral()
    gradlePluginPortal() // ✅ Ensures Kotlin and other JetBrains plugins can be resolved
    maven("https://plugins.jetbrains.com/maven")
}

intellij {
    version.set("2025.1.3")
    type.set("IC")
    plugins.set(listOf("com.intellij.java", "org.jetbrains.plugins.gradle"))
}


dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0") // for REST
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1") // for JSON
}




tasks {
    patchPluginXml {
        sinceBuild.set("241.0")
        untilBuild.set("261.*")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }

    compileJava {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

//    runIde {
//        ideDirectory.set(file("/home/YOUR_USERNAME/.local/share/JetBrains/Toolbox/apps/IDEA-C/ch-0/241.26927.53")) // OPTIONAL: Path to your IntelliJ CE install if required
//    }
}
