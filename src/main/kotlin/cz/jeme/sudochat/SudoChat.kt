package cz.jeme.sudochat

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

@Suppress("UnstableApiUsage")
class SudoChat : JavaPlugin() {
    override fun onEnable() {
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) {
            SudoCommand(this, it.registrar())
        }
    }
}