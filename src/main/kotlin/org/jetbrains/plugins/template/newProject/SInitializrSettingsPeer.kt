package org.jetbrains.plugins.template.newproject

import com.intellij.ide.util.projectWizard.SettingsStep
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.platform.ProjectGeneratorPeer
import com.intellij.platform.WebProjectGenerator
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import org.jetbrains.plugins.template.settings.SInitializrSettings
import javax.swing.JComponent

class SInitializrSettingsPeer : ProjectGeneratorPeer<SInitializrSettings>
{

    private val settings = SInitializrSettings()

    private val myPanel: JComponent = panel {
        row("Group ID:") {
            textField()
                .bindText(settings::groupId)
        }
        row("Artifact ID:") {
            textField()
                .bindText(settings::artifactId)
        }
        row("Dependencies (comma-separated):") {
            textField()
                .bindText(settings::dependencies)
        }
    }

    override fun getComponent(): JComponent = myPanel

    override fun buildUI(settingsStep: SettingsStep) {
        settingsStep.addSettingsComponent(myPanel)
        // No need for addValidator() — validate() will be called automatically
    }

    override fun getSettings(): SInitializrSettings = settings

    override fun validate(): ValidationInfo? {
        if (settings.groupId.isBlank()) {
            return ValidationInfo("Group ID must not be empty")
        }
        if (!settings.groupId.matches(Regex("^[a-zA-Z_][a-zA-Z0-9_.]*$"))) {
            return ValidationInfo("Group ID contains invalid characters")
        }

        if (settings.artifactId.isBlank()) {
            return ValidationInfo("Artifact ID must not be empty")
        }
        if (!settings.artifactId.matches(Regex("^[a-zA-Z0-9_-]+$"))) {
            return ValidationInfo("Artifact ID contains invalid characters")
        }

        if (settings.dependencies.isNotBlank() &&
            !settings.dependencies.matches(Regex("^[a-zA-Z0-9_\\-,\\s]*$"))) {
            return ValidationInfo("Dependencies must be comma-separated names (letters, numbers, hyphens)")
        }

        return null
    }

    override fun isBackgroundJobRunning(): Boolean = false

    override fun addSettingsStateListener(listener: WebProjectGenerator.SettingsStateListener) {}

    fun disposeUIResources() {}
}
