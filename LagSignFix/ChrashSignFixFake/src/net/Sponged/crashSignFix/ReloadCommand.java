package net.Sponged.crashSignFix;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if ((((sender instanceof Player)) && (sender.hasPermission("CrashSignFix.admin.reload"))) || (!(sender instanceof Player)))
    {
      Plugin.getInstance().reloadConfig();
      Plugin.getInstance().config = Plugin.getInstance().getConfig();
      sender.sendMessage("§aThe Crash Sign Fix Config has been reloaded");
    }
    else
    {
      sender.sendMessage(Plugin.getInstance().config.getString("noPermissionMessage", "§cYou are not allowed to execute this command!").replace("&", "§").replace("§§", "&"));
    }
    return true;
  }
}
