package org.jetbrains.plugins.template.settings

import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class PluginSettingsConfigurable : Configurable {

    private var settingsComponent: PluginSettingsComponent? = null

    override fun getDisplayName(): String {
        return "S-Initializr4CE Settings"
    }

    override fun createComponent(): JComponent {
        settingsComponent = PluginSettingsComponent()
        return settingsComponent!!.getPanel()
    }

    override fun isModified(): Boolean {
        val settingsState = service<PluginSettingsState>().state
        return settingsComponent?.let {
            it.getGroupId() != settingsState.groupId ||
                    it.getArtifactId() != settingsState.artifactId ||
                    it.getBootVersion() != settingsState.bootVersion ||
                    it.getJavaVersion() != settingsState.javaVersion ||
                    it.getPackaging() != settingsState.packaging ||
                    it.getDependencies() != settingsState.dependencies
        } ?: false
    }


    override fun apply() {
        settingsComponent?.let {
            val groupId = it.getGroupId()
            val artifactId = it.getArtifactId()
            val bootVersion = it.getBootVersion()
            val javaVersion = it.getJavaVersion()
            val packaging = it.getPackaging()
            val dependencies = it.getDependencies()
            val destinationPath = System.getProperty("user.home") // Or retrieve from UI if editable

            service<PluginSettingsState>().updateState(
                groupId,
                artifactId,
                bootVersion,
                javaVersion,
                packaging,
                dependencies,
                destinationPath
            )
        }
    }


    override fun reset() {
        val settings = service<PluginSettingsState>().state
        settingsComponent?.let {
            it.setGroupId(settings.groupId)
            it.setArtifactId(settings.artifactId)
            it.setBootVersion(settings.bootVersion)
            it.setJavaVersion(settings.javaVersion)
            it.setPackaging(settings.packaging)
            it.setDependencies(settings.dependencies)
        }
    }


    override fun disposeUIResources() {
        settingsComponent = null
    }
}
