package cz.jeme.programu.sudochat;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class SudoChat extends JavaPlugin {

	public static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + ChatColor.BOLD + "SudoChat"
			+ ChatColor.DARK_GRAY + "]: " + ChatColor.RESET;
	public static final String EVERYONE = "@everyone";

	@Override
	public void onEnable() {
		getCommand("sudo").setTabCompleter(new CommandTabCompleter());
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("sudo")) {
			if (args.length < 2) {
				sender.sendMessage(PREFIX + ChatColor.RED + "Not enough arguments!");
				return true;
			}

			String playerSelector = args[0];
			StringBuffer buffer = new StringBuffer(30);

			for (int i = 1; i < args.length; i++) {
				buffer.append(args[i]);

				if (i != args.length - 1) {
					buffer.append(" ");
				}

			}

			String message = buffer.toString();

			String task = "Sent";
			if (message.charAt(0) == '/') {
				task = "Executed";
			}

			if (playerSelector.equals(EVERYONE)) {
				sender.sendMessage(PREFIX + ChatColor.GREEN + task + " \"" + ChatColor.DARK_GREEN + message
						+ ChatColor.GREEN + "\" as " + ChatColor.DARK_GREEN + "@everyone");
				chat(Bukkit.getOnlinePlayers(), message);
				return true;
			}
			Player player = Bukkit.getPlayerExact(playerSelector);
			if (player == null) {
				sender.sendMessage(PREFIX + ChatColor.RED + "Player not found!");
				return true;
			}

			sender.sendMessage(PREFIX + ChatColor.GREEN + task + " \"" + ChatColor.DARK_GREEN + message
					+ ChatColor.GREEN + "\" as player " + ChatColor.DARK_GREEN + playerSelector);
			chat(player, message);
			return true;
		}

		return false;
	}

	private void chat(Player player, String message) {
		player.chat(message);
	}

	private void chat(Collection<? extends Player> players, String message) {
		for (Player player : players) {
			chat(player, message);
		}
	}
}