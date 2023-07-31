package cz.jeme.programu.sudochat;

import org.bukkit.plugin.java.JavaPlugin;

public class SudoChat extends JavaPlugin {
	@Override
	public void onEnable() {
		new SudoCommand();
	}
}