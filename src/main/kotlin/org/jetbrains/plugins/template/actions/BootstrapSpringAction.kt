package org.jetbrains.plugins.template.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFileManager
import org.jetbrains.plugins.template.services.SpringInitializrService
import java.io.File

class BootstrapSpringAction : AnAction("Spring Initializr - Bootstrap Project") {

    override fun actionPerformed(e: AnActionEvent) {
        val project: Project = e.project ?: return
        val basePath = project.basePath ?: return
        val destination = File(basePath)

        try {
            SpringInitializrService.generateProject(
                groupId = "com.example",
                artifactId = "demo",
                bootVersion = "3.1.5",
                javaVersion = "17",
                packaging = "jar",
                dependencies = listOf("web", "jpa", "h2"),
                destination = destination
            )

            // Refresh the IDE's file system to show new files
            VirtualFileManager.getInstance().syncRefresh()

            Messages.showInfoMessage(
                project,
                "Spring Boot project generated successfully!",
                "S-Initializr4CE"
            )

        } catch (ex: Exception) {
            Messages.showErrorDialog(
                project,
                "Failed to generate Spring Boot project:\n${ex.message}",
                "S-Initializr4CE - Error"
            )
        }
    }
}
