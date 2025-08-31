package org.jetbrains.plugins.template.newProject

import com.intellij.icons.AllIcons
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.ide.wizard.AbstractNewProjectWizardStep
import com.intellij.ide.wizard.GeneratorNewProjectWizard
import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import org.jetbrains.plugins.template.settings.PluginSettingsState
import org.jetbrains.plugins.template.services.SpringInitializrService
import java.io.File
import javax.swing.Icon

class SInitializrNewProjectWizard : GeneratorNewProjectWizard {
    override val name: String = "S-Initializr4CE"
    override val id: String = "s-initializr4ce"
    override val description: String = "Generate Spring Boot project using Spring Initializr API"
    override val icon: Icon = AllIcons.Nodes.Module

    override fun createStep(context: WizardContext): NewProjectWizardStep {
        return SInitializrWizardStep(parentStep = null, wizardContext = context)
    }
}

class SInitializrWizardStep(
    parentStep: NewProjectWizardStep?,
    private val wizardContext: WizardContext
) : AbstractNewProjectWizardStep(
    parentStep!!
), NewProjectWizardStep {

    private var groupId: String = "com.example"
    private var artifactId: String = "demo"

    override fun setupProject(project: Project) {
        val settings = PluginSettingsState.state
        val deps: List<String> = when (val d = settings.dependencies) {
            is String -> if (d.isNotEmpty()) listOf(d) else {
                TODO()
            }
            else -> (d as? List<*>)?.filterIsInstance<String>() ?: emptyList()
        }
        val destPath = wizardContext.projectFileDirectory
        val destination = File(destPath)
        SpringInitializrService.generateProject(
            groupId = groupId,
            artifactId = artifactId,
            bootVersion = settings.bootVersion,
            javaVersion = settings.javaVersion,
            packaging = settings.packaging,
            dependencies = deps,
            destination = destination
        )
        LocalFileSystem.getInstance().refreshAndFindFileByIoFile(destination)?.let {
            VfsUtil.markDirtyAndRefresh(true, true, true, it)
        }
    }
}
