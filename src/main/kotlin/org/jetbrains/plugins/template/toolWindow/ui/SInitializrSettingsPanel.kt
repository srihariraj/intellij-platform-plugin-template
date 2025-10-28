package org.jetbrains.plugins.template.toolWindow.ui

import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import org.jetbrains.plugins.template.settings.SInitializrSettings
import javax.swing.JComponent

class SInitializrSettingsPanel(
    private val settings: SInitializrSettings
) {

    private val groupIdField = JBTextField().apply { text = settings.groupId }
    private val artifactIdField = JBTextField().apply { text = settings.artifactId }
    private val dependenciesField = JBTextField().apply { text = settings.dependencies }

    private val mainPanel = panel {
        row("Group ID:") { cell(groupIdField).align(
            AlignX.FILL) }
        row("Artifact ID:") { cell(artifactIdField).align(
            AlignX.FILL) }
        row("Dependencies (comma-separated):") { cell(dependenciesField).align(AlignX.FILL) }
    }

    fun getComponent(): JComponent = mainPanel

    fun validate(): ValidationInfo? {
        val groupId = groupIdField.text.trim()
        val artifactId = artifactIdField.text.trim()
        val dependencies = dependenciesField.text.trim()

        if (groupId.isEmpty()) return ValidationInfo("Group ID must not be empty", groupIdField)
        if (!groupId.matches(Regex("^[a-zA-Z_][a-zA-Z0-9_.]*\$")))
            return ValidationInfo("Group ID contains invalid characters", groupIdField)

        if (artifactId.isEmpty()) return ValidationInfo("Artifact ID must not be empty", artifactIdField)
        if (!artifactId.matches(Regex("^[a-zA-Z0-9_-]+\$")))
            return ValidationInfo("Artifact ID contains invalid characters", artifactIdField)

        if (dependencies.isNotEmpty() && !dependencies.matches(Regex("^[a-zA-Z0-9_\\-,\\s]*\$")))
            return ValidationInfo("Dependencies must be comma-separated names", dependenciesField)

        // Save inputs back to settings
        settings.groupId = groupId
        settings.artifactId = artifactId
        settings.dependencies = dependencies
        return null
    }
}
