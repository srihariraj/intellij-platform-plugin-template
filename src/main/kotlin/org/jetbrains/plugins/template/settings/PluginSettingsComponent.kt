package org.jetbrains.plugins.template.settings

import javax.swing.*
import java.awt.GridLayout

class PluginSettingsComponent {
    val panel: JPanel = JPanel(GridLayout(0, 2))

    private val groupIdField = JTextField()
    private val artifactIdField = JTextField()
    private val bootVersionField = JTextField()
    private val javaVersionField = JTextField()
    private val packagingField = JTextField()
    private val dependenciesField = JTextField()

    init {
        panel.add(JLabel("Group ID:"))
        panel.add(groupIdField)

        panel.add(JLabel("Artifact ID:"))
        panel.add(artifactIdField)

        panel.add(JLabel("Spring Boot Version:"))
        panel.add(bootVersionField)

        panel.add(JLabel("Java Version:"))
        panel.add(javaVersionField)

        panel.add(JLabel("Packaging (jar/war):"))
        panel.add(packagingField)

        panel.add(JLabel("Dependencies (comma-separated):"))
        panel.add(dependenciesField)
    }

    fun getGroupId() = groupIdField.text
    fun setGroupId(value: String) { groupIdField.text = value }

    fun getArtifactId() = artifactIdField.text
    fun setArtifactId(value: String) { artifactIdField.text = value }

    fun getBootVersion() = bootVersionField.text
    fun setBootVersion(value: String) { bootVersionField.text = value }

    fun getJavaVersion() = javaVersionField.text
    fun setJavaVersion(value: String) { javaVersionField.text = value }

    fun getPackaging() = packagingField.text
    fun setPackaging(value: String) { packagingField.text = value }

    fun getDependencies() = dependenciesField.text
    fun setDependencies(value: String) { dependenciesField.text = value }

    fun getPanel() = panel
}
