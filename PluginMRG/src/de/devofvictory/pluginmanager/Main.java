package de.devofvictory.pluginmanager;

import org.bukkit.plugin.java.JavaPlugin;

import de.devofvictory.pluginmanager.commands.Plugin;
 
 public class Main extends JavaPlugin
 {
   public void onEnable()
   {
     register();
     super.onEnable();
   }
   
   private void register() {
     getCommand("plugin").setExecutor(new Plugin());
   }
 }


