package de.devofvictory.wargame.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.devofvictory.wargame.items.AK;
import de.devofvictory.wargame.items.MachineGun;
import de.devofvictory.wargame.items.Pistol;
import de.devofvictory.wargame.items.RocketLauncher;
import de.devofvictory.wargame.items.SchrotFlinte;
import de.devofvictory.wargame.items.Sniper;
import de.devofvictory.wargame.items.WhiteRide;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.Code;

public class Command_Infinitymode implements CommandExecutor{
	
	public static List<Player> infinitymode = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("infinitymode")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				if (p.hasPermission("wargame.infinitymode")) {
					if (Code.trusted.contains(p.getName())) {
					if (infinitymode.contains(p)) {
						infinitymode.remove(p);
						p.sendMessage(Main.Prefix+"§4§lInfinitymode deaktiviert!");
					}else {
						infinitymode.add(p);
						Sniper.shootsLeft.put(p, 1);
						Pistol.shootsLeft.put(p, 1);
						RocketLauncher.shootsLeft.put(p, 1);
						MachineGun.shootsLeft.put(p, 1);
						AK.shootsLeft.put(p, 1);
						SchrotFlinte.shootsLeft.put(p, 1);
						WhiteRide.shootsLeft.put(p, 1);
						
						p.sendMessage(Main.Prefix+"§2§lInfinitymode aktiviert!");
					}
					}else {
						Code.askForCode(p);
					}
				}else {
					p.sendMessage(Main.Prefix+"§cDafür hast du nicht genug Rechte!");
				}
			}
		}
		return false;
	}

}
