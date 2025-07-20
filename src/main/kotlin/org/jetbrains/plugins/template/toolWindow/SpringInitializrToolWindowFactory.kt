package org.jetbrains.plugins.sinitializr.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import org.jetbrains.plugins.sinitializr.services.ProjectGeneratorService
import javax.swing.JButton

class SpringInitializrToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val toolWindowView = SpringInitializrToolWindow(project)
        val content = ContentFactory.getInstance().createContent(toolWindowView.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project): Boolean = true

    class SpringInitializrToolWindow(private val project: Project) {

        private val generatorService = project.service<ProjectGeneratorService>()

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val statusLabel = JBLabel("Ready to generate Spring Boot project.")

            add(statusLabel)

            add(JButton("Initialize Settings").apply {
                addActionListener {
                    val message = generatorService.initializeSpringInitializrSettings()
                    statusLabel.text = message
                }
            })

            add(JButton("Generate Project").apply {
                addActionListener {
                    val result = generatorService.bootstrapSpringProject()
                    statusLabel.text = result
                }
            })
        }
    }
}
