package de.devofvictory.pluginmanager.commands;

import de.devofvictory.pluginmanager.utils.Manager;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Plugin
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((sender instanceof Player))
    {
      Player p = (Player)sender;
      if (p.hasPermission("pluginmanager.admin"))
      {
        if (args.length == 0) {
          p.sendMessage("§c/plugin <download/custom/version/enable/disable/reload>");
        } else if (args[0].equalsIgnoreCase("enable"))
        {
          if (args.length == 2)
          {
            String plugin = args[1];
            if (Bukkit.getPluginManager().getPlugin(plugin) != null)
            {
              if (Bukkit.getPluginManager().getPlugin(plugin).isEnabled())
              {
                p.sendMessage("§cDieses Plugin ist bereits aktiviert!");
              }
              else
              {
                Bukkit.getPluginManager().getPlugin(plugin).getPluginLoader().enablePlugin(Bukkit.getPluginManager().getPlugin(plugin));
                p.sendMessage("§aPlugin §e" + plugin + " §awurde aktiviert!");
              }
            }
            else {
              p.sendMessage("§cDieses Plugin gibt es nicht!");
            }
          }
          else
          {
            p.sendMessage("§c/plugin enable <plugin>");
          }
        }
        else if (args[0].equalsIgnoreCase("disable"))
        {
          if (args.length == 2)
          {
            String plugin = args[1];
            if (Bukkit.getPluginManager().getPlugin(plugin) != null)
            {
              if (!Bukkit.getPluginManager().getPlugin(plugin).isEnabled())
              {
                p.sendMessage("§cDieses Plugin ist bereits deaktiviert!");
              }
              else
              {
                Bukkit.getPluginManager().getPlugin(plugin).getPluginLoader().disablePlugin(Bukkit.getPluginManager().getPlugin(plugin));
                p.sendMessage("§aPlugin §e" + plugin + " §awurde deaktiviert!");
              }
            }
            else {
              p.sendMessage("§cDas Plugin gibt es nicht");
            }
          }
          else
          {
            p.sendMessage("§c/plugin disable <plugin>");
          }
        }
        else if (args[0].equalsIgnoreCase("reload"))
        {
          if (args.length == 2)
          {
            String plugin = args[1];
            if (Bukkit.getPluginManager().getPlugin(plugin) != null)
            {
              Bukkit.getPluginManager().getPlugin(plugin).getPluginLoader().disablePlugin(Bukkit.getPluginManager().getPlugin(plugin));
              Bukkit.getPluginManager().getPlugin(plugin).getPluginLoader().enablePlugin(Bukkit.getPluginManager().getPlugin(plugin));
              p.sendMessage("§aPlugin §e" + plugin + " §awas reloaded!");
            }
            else
            {
              p.sendMessage("§cDas Plugin gibt es nicht");
            }
          }
          else
          {
            p.sendMessage("§c/plugin reload <plugin>");
          }
        }
        else if (args[0].equalsIgnoreCase("download"))
        {
          if (args.length == 3)
          {
            String name = args[2];
            String id = args[1];
            File file = new File("plugins/" + name + ".jar");
            if (!file.exists()) {
              try
              {
                file.createNewFile();
              }
              catch (IOException e)
              {
                e.printStackTrace();
              }
            }
            Manager.downloadFile(file, Manager.getPluginDownloadURL(id));
            p.sendMessage("§aPlugin §e" + name + " §awurde heruntergeladen!");
          }
          else
          {
            p.sendMessage("§c/plugin download <id> <name>");
          }
        }
        else if (args[0].equalsIgnoreCase("version"))
        {
          if (args.length == 2)
          {
            String id = args[1];
            p.sendMessage("§aDie neuste Version von §e" + id + " §aist Version §e" + Manager.getPluginVersion(id));
          }
          else
          {
            p.sendMessage("§c/plugin version <id>");
          }
        }
        else if (args[0].equalsIgnoreCase("custom"))
        {
          if (args.length == 3)
          {
            String name = args[2];
            String url = args[1];
            File file = new File("plugins/" + name + ".jar");
            if (!file.exists()) {
              try
              {
                file.createNewFile();
              }
              catch (IOException e)
              {
                e.printStackTrace();
              }
            }
            Manager.downloadFile(file, url);
            p.sendMessage("§aPlugin §e" + name + " §awurde heruntergeladen!");
          }
          else
          {
            p.sendMessage("§c/plugin custom <download-url> <name>");
          }
        }
        else {
          p.sendMessage("§c/plugin <download/custom/version/enable/disable/reload>");
        }
      }
      else {
        p.sendMessage("§cKeine Rechte. // Dir fehlt (pluginmanager.admin)");
      }
    }
    return false;
  }
}
