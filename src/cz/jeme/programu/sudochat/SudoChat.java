package cz.jeme.programu.sudochat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SudoChat extends JavaPlugin {

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("sudo")) { // Command was sent
			if (args.length < 2) {
				return false;
			} else {
				String selectedPlayerName = args[0];
				StringBuffer whatToWrite = new StringBuffer(30);
				for (int i = 1; i < args.length; i++) {
					whatToWrite.append(args[i]);
					if (i != args.length - 1) {
						whatToWrite.append(" ");
					}
				}
				Player selectedPlayer = Bukkit.getPlayerExact(selectedPlayerName);
				if (selectedPlayer == null) {
					sender.sendMessage(ChatColor.RED + "The selected player is not online!");
					return true;
				} else {
					selectedPlayer.chat(whatToWrite.toString());
					sender.sendMessage(ChatColor.GREEN + "Succesfully said \"" + whatToWrite + "\" as player " + selectedPlayerName + "!");
					return true;
				}
					

			}
		}
		return false;
	}
}