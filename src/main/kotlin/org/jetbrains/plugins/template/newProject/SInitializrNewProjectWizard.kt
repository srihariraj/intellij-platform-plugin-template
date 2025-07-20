package org.jetbrains.plugins.template.newProject

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.ide.wizard.AbstractNewProjectWizardStep
import com.intellij.ide.wizard.GeneratorNewProjectWizard
import com.intellij.ide.wizard.NewProjectWizardLanguageStep
import com.intellij.ide.wizard.NewProjectWizardStep
import com.intellij.ide.wizard.LanguageNewProjectWizard
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.openapi.observable.properties.StringProperty
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.JavaSdk
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.util.UserDataHolder
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.platform.ProjectGeneratorPeer
import com.intellij.platform.WebProjectGenerator
import com.intellij.platform.util.newWizard.IntelliJNewProjectWizard
import com.intellij.platform.util.newWizard.NewProjectWizardChainStep
import com.intellij.platform.util.newWizard.generators.GeneratorNewProjectWizard
import com.intellij.platform.util.newWizard.generators.GeneratorNewProjectWizardStep
import org.jetbrains.plugins.template.settings.PluginSettingsState
import org.jetbrains.plugins.template.services.SpringInitializrService
import java.io.File
import javax.swing.Icon

class SInitializrNewProjectWizard :
    GeneratorNewProjectWizard {

    override val name: String = "S-Initializr4CE"
    override val id: String = "s-initializr4ce"
    override val description: String = "Generate Spring Boot project using Spring Initializr API"
    override val icon: Icon = null // Replace with your custom plugin icon if available

    override fun createStep(
        context: WizardContext
    ): NewProjectWizardStep {
        return SInitializrWizardStep(parent)
    }
}

class SInitializrWizardStep(parent: NewProjectWizardStep) :
    GeneratorNewProjectWizardStep(parent, SInitializrNewProjectWizard()),
    NewProjectWizardStep {
    override val context: WizardContext
        get() = TODO(
            "Not yet implemented"
        )

    private val propertyGraph = PropertyGraph()
    override val keywords: NewProjectWizardStep.Keywords
        get() = TODO(
            "Not yet implemented"
        )
    override val data: UserDataHolder
        get() = TODO(
            "Not yet implemented"
        )
    private val groupId: StringProperty = propertyGraph.property("com.example")
    private val artifactId: StringProperty = propertyGraph.property("demo")

    override fun setupProject(project: Project) {
        val settings = PluginSettingsState.state

        SpringInitializrService.generateProject(
            groupId = groupId.get(),
            artifactId = artifactId.get(),
            bootVersion = settings.bootVersion,
            javaVersion = settings.javaVersion,
            packaging = settings.packaging,
            dependencies = settings.dependencies,
            destination = File(contentRoot.path)
        )

        // Refresh the project directory
        LocalFileSystem.getInstance().refreshAndFindFileByIoFile(File(contentRoot.path))?.let {
            VfsUtil.markDirtyAndRefresh(true, true, true, it)
        }
    }
}
