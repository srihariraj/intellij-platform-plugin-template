package org.jetbrains.plugins.template.newProject

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.DirectoryProjectGenerator
import com.intellij.platform.DirectoryProjectGeneratorBase
import com.intellij.platform.ProjectGeneratorPeer
import org.jetbrains.plugins.template.newproject.SInitializrSettingsPeer
import org.jetbrains.plugins.template.settings.PluginSettingsState
import org.jetbrains.plugins.template.services.SpringInitializrService
import java.io.File
import javax.swing.Icon

class SInitializrNewProjectGenerator :
    DirectoryProjectGeneratorBase<SInitializrSettings>() {
    override fun getName(): @NlsContexts.Label String {
        TODO(
            "Not yet implemented"
        )
    }

    override fun createPeer(): ProjectGeneratorPeer<SInitializrSettings> {
        return SInitializrSettingsPeer() as ProjectGeneratorPeer<SInitializrSettings>
    }

    override fun getLogo(): Icon? {
        TODO(
            "Not yet implemented"
        )
    }


    override fun generateProject(
        project: Project,
        baseDir: VirtualFile,
        settings: SInitializrSettings,
        module: Module
    ) {
        val contentRoot = File(baseDir.path)

        SpringInitializrService.generateProject(
            groupId = settings.groupId,
            artifactId = settings.artifactId,
            bootVersion = settings.bootVersion,
            javaVersion = settings.javaVersion,
            packaging = settings.packaging,
            dependencies = settings.dependencies.split(","),
            destination = contentRoot
        )
    }
}

// Plain settings data holder
data class SInitializrSettings(
    var groupId: String = PluginSettingsState.state.groupId,
    var artifactId: String = PluginSettingsState.state.artifactId,
    var bootVersion: String = PluginSettingsState.state.bootVersion,
    var javaVersion: String = PluginSettingsState.state.javaVersion,
    var packaging: String = PluginSettingsState.state.packaging,
    var dependencies: String = PluginSettingsState.state.dependencies
)


