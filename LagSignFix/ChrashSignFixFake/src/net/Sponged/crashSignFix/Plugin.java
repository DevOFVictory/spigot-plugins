package net.Sponged.crashSignFix;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin
  extends JavaPlugin
  implements Listener
{
  private static Plugin instance;
  FileConfiguration config;
  
  public void onEnable()
  {
    instance = this;
    
    this.config = getConfig();
    saveDefaultConfig();
    
    getCommand("reloadCrashSignFix").setExecutor(new ReloadCommand());
    
    getServer().getPluginManager().registerEvents(this, this);
  }
  
  public void onDisable() {}
  
  @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
  public void onSignChange(SignChangeEvent event)
  {
    if ((event.getPlayer().hasPermission("CrashSignFix.admin.bypass")) && (this.config.getBoolean("allowBypass", false))) {
      return;
    }
    boolean isBad = false;
    String[] arrayOfString;
    int j = (arrayOfString = event.getLines()).length;
    for (int i = 0; i < j; i++)
    {
      String s = arrayOfString[i];
      if (s.length() > this.config.getInt("maxAllowedCharCount", 50))
      {
        isBad = true;
        if (this.config.getBoolean("informPlayer", true)) {
          event.getPlayer().sendMessage(this.config.getString("playerInformationMessage", "§cYou are not allowed to use Lag Sign on this Server!").replace("&", "§").replace("§§", "&").replace("<Player>", event.getPlayer().getName()).replace("<Char Count>", String.valueOf(s.length())));
        }
        if (this.config.getBoolean("sendMessageToAdmins", true)) {
          for (Player p : getServer().getOnlinePlayers()) {
            if (p.hasPermission("CrashSignFix.admin.notify")) {
              p.sendMessage(this.config.getString("messageToSendToAdmins", "§c<Player> tried to place a Lag Sign. Char count: §6<Char Count>").replace("&", "§").replace("§§", "&").replace("<Player>", event.getPlayer().getName()).replace("<Char Count>", String.valueOf(s.length())));
            }
          }
        }
        if (!this.config.getBoolean("executeCommandOnAbuse", false)) {
          break;
        }
        ConsoleCommandSender sender = getServer().getConsoleSender();
        getServer().dispatchCommand(sender, this.config.getString("commandToExecute", "kick <Player> §cDon't do that! That's not friendly.").replace("&", "§").replace("§§", "&").replace("<Player>", event.getPlayer().getName()).replace("<Char Count>", String.valueOf(s.length())));
        
        break;
      }
    }
    if (isBad) {
      if (this.config.getBoolean("replaceSignTexts", true))
      {
        event.setLine(0, this.config.getString("line1Text", "You are").replace("&", "§").replace("§§", "&").replace("<Player>", event.getPlayer().getName()));
        event.setLine(1, this.config.getString("line2Text", "not allowed").replace("&", "§").replace("§§", "&").replace("<Player>", event.getPlayer().getName()));
        event.setLine(2, this.config.getString("line3Text", "to do").replace("&", "§").replace("§§", "&").replace("<Player>", event.getPlayer().getName()));
        event.setLine(3, this.config.getString("line4Text", "this here!").replace("&", "§").replace("§§", "&").replace("<Player>", event.getPlayer().getName()));
      }
      else
      {
        event.setCancelled(true);
      }
    }
  }
  
  public static Plugin getInstance()
  {
    return instance;
  }
}
