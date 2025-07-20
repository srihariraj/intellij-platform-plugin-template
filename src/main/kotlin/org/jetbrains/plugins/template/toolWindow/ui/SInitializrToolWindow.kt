package org.jetbrains.plugins.template.toolWindow.ui

import com.intellij.openapi.project.Project
import java.awt.Dimension
import javax.swing.*

class SInitializrToolWindow(private val project: Project) {
    fun getContent(): JPanel {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        val groupField = JTextField("com.example")
        val artifactField = JTextField("demo")
        val versionField = JTextField("3.2.5") // Default Spring Boot version
        val dependenciesField = JTextField("web,security")

        val generateButton = JButton("Generate Project")
        generateButton.addActionListener {
            JOptionPane.showMessageDialog(panel, "Scaffolding project with dependencies: ${dependenciesField.text}")
            // TODO: Add actual scaffolding logic
        }

        // Labels
        panel.add(JLabel("Group"))
        panel.add(groupField)
        panel.add(JLabel("Artifact"))
        panel.add(artifactField)
        panel.add(JLabel("Spring Boot Version"))
        panel.add(versionField)
        panel.add(JLabel("Dependencies (comma-separated)"))
        panel.add(dependenciesField)
        panel.add(Box.createRigidArea(Dimension(0, 10)))
        panel.add(generateButton)

        return panel
    }
}
