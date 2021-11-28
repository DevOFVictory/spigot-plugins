package de.devofvictory.wargame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.BossBar;
import de.devofvictory.wargame.utils.SpectatorClass;
import de.devofvictory.wargame.utils.StartGame;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;

public class Listener_OnQuit implements Listener{
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		BossBar.remove(p);
		
		if (!StartGame.spectators.contains(p)) {
			e.setQuitMessage(Main.Prefix+"§6"+p.getName()+" §dhat das Spiel verlassen!");
			
		}else {
			e.setQuitMessage("");
		}
		
		if (StartGame.living.contains(p)) {
			StartGame.living.remove(p);
		}
		
		
		
		if (StartGame.ride.containsKey(p.getName()))
			StartGame.ride.get(p.getName()).remove();
		
		p.setLevel(0);
		
		

		
		if (StartGame.living.size() < 2 && Main.isMatchRunning) {
			
			for (Player living : StartGame.living) {
				
				StartGame.setWinner(living);
			}
			
		}
		
		if (StartGame.living.size() == 0 && Main.isGameRunning) {
			Bukkit.shutdown();
		}
		
		if (SpectatorClass.specCamera.containsValue(p.getName())) {
			for (String key : SpectatorClass.specCamera.keySet()) {
				if (SpectatorClass.specCamera.get(key).equalsIgnoreCase(p.getName())) {
					Player keyPlayer = Bukkit.getPlayer(key);
					
					PacketPlayOutCamera camera = new PacketPlayOutCamera();
					camera.a = keyPlayer.getEntityId();
					((CraftPlayer)keyPlayer).getHandle().playerConnection.sendPacket(camera);
					keyPlayer.setGameMode(GameMode.SURVIVAL);
					SpectatorClass.specCamera.remove(keyPlayer.getName());

					keyPlayer.sendMessage(Main.Prefix+"§cDu beobachtest §6"+p.getName()+" §cnun nicht mehr!");
				}
			}
			
		}
	}

}
