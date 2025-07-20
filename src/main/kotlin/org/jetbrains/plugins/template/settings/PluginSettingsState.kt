package org.jetbrains.plugins.template.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "SInitializr4CESettings",
    storages = [Storage("SInitializr4CE.xml")]
)
@Service
object PluginSettingsState : PersistentStateComponent<PluginSettingsState.State> {

    data class State(
        var groupId: String = "com.example",
        var artifactId: String = "demo",
        var bootVersion: String = "3.2.5",          // ✅ added
        var javaVersion: String = "17",             // ✅ added
        var packaging: String = "jar",              // ✅ added
        var dependencies: String = "web",
        var destinationPath: String = System.getProperty("user.home")
    )

    private var myState = State()

    override fun getState(): State = myState

    override fun loadState(state: State) {
        myState = state
    }

    fun updateState(
        groupId: String,
        artifactId: String,
        bootVersion: String,
        javaVersion: String,
        packaging: String,
        dependencies: String,
        destinationPath: String
    ) {
        myState = State(groupId, artifactId, bootVersion, javaVersion, packaging, dependencies, destinationPath)
    }
}
