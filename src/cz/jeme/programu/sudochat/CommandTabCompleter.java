package cz.jeme.programu.sudochat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			List<String> playerNames = new ArrayList<String>();

			for (Player player : Bukkit.getOnlinePlayers()) {
				playerNames.add(player.getName());
			}
			playerNames.add(SudoChat.EVERYONE);
			return playerNames;
		}
		return new ArrayList<String>();
	}
}
