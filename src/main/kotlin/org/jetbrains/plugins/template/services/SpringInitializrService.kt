package org.jetbrains.plugins.template.services

import java.io.File
import java.net.URL
import java.net.URLEncoder
import java.nio.file.Files
import java.util.zip.ZipInputStream

object SpringInitializrService {

    fun generateProject(
        groupId: String,
        artifactId: String,
        bootVersion: String,
        javaVersion: String,
        packaging: String,
        dependencies: List<String>,
        destination: File
    ) {
        val params = mapOf(
            "type" to "maven-project",
            "language" to "java",
            "bootVersion" to bootVersion,
            "baseDir" to artifactId,
            "groupId" to groupId,
            "artifactId" to artifactId,
            "name" to artifactId,
            "javaVersion" to javaVersion,
            "packaging" to packaging,
            "dependencies" to dependencies.joinToString(",")
        )

        val query = params.entries.joinToString("&") { (key, value) ->
            "${URLEncoder.encode(key, "UTF-8")}=${URLEncoder.encode(value, "UTF-8")}"
        }

        val url = URL("https://start.spring.io/starter.zip?$query")
        val connection = url.openConnection()

        ZipInputStream(connection.getInputStream()).use { zip ->
            var entry = zip.nextEntry
            while (entry != null) {
                val outPath = File(destination, entry.name)
                if (entry.isDirectory) {
                    outPath.mkdirs()
                } else {
                    outPath.parentFile.mkdirs()
                    Files.copy(zip, outPath.toPath())
                }
                entry = zip.nextEntry
            }
        }
    }
}
