package org.jetbrains.plugins.template.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@State(
    name = "PluginSettings",
    storages = [Storage("SInitializrPluginSettings.xml")]
)
@Service(Service.Level.PROJECT)
class PluginSettings : PersistentStateComponent<PluginSettings.State> {

    private var state = State()

    companion object {
        fun getInstance(project: Project): PluginSettings {
            return project.getService(PluginSettings::class.java)
        }
    }

    class State {
        var groupId: String = "com.example"
        var artifactId: String = "demo"
        var bootVersion: String = "3.2.5"
        var javaVersion: String = "17"
        var packaging: String = "jar"
        var dependencies: String = "web"
    }

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }
}
