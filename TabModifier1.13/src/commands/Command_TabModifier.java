package commands;



import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.Main;
import utils.Util_Tab;


public class Command_TabModifier implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("tabmodifier.reload")) {
					
					Main.getInstance().reloadConfig();

					Main.getInstance().buildHeader();
					Main.getInstance().buildFooter();

						

						for (Player all : Bukkit.getOnlinePlayers()) {
							Util_Tab.sendTab(all, Main.headerOutput.replace('&', '§'), Main.footerOutput.replace('&', '§'));
						}
						
						Main.Prefix = Main.getInstance().getConfig().getString("Prefix").replace('&', '§');
						
						sender.sendMessage(Main.Prefix+"§aConfig file was reload!");
					
				}else {
					sender.sendMessage(Main.Prefix+"§cYou dont have enough permissions.");
				}
			}else {
				sender.sendMessage(Main.Prefix+"§cCouldn't found subcommand §6"+args[0]+"§c! Did you mean §6reload§c?");
			}
		}else {
			sender.sendMessage(Main.Prefix+"§cUsage: §6/tabmodifier reload");
		}
		
		
		return true;
	}

}
