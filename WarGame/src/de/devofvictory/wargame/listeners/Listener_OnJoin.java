package de.devofvictory.wargame.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import de.chilipro.mt.objects.PlayerUUIDStats;
import de.devofvictory.wargame.items.AK;
import de.devofvictory.wargame.items.MachineGun;
import de.devofvictory.wargame.items.Pistol;
import de.devofvictory.wargame.items.RocketLauncher;
import de.devofvictory.wargame.items.SchrotFlinte;
import de.devofvictory.wargame.items.Sniper;
import de.devofvictory.wargame.items.WhiteRide;
import de.devofvictory.wargame.main.Main;
import de.devofvictory.wargame.utils.ClearUtil;
import de.devofvictory.wargame.utils.EntityModifier;
import de.devofvictory.wargame.utils.Holograms;
import de.devofvictory.wargame.utils.ScoreBoardManager;
import de.devofvictory.wargame.utils.SpectatorClass;
import de.devofvictory.wargame.utils.SpindManager;
import de.devofvictory.wargame.utils.StartWartePhase;

public class Listener_OnJoin implements Listener{
	
	
	public static ArrayList<Player> alreadyJoined = new ArrayList<>();
	
	private static boolean firstJoin = true;
	
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		
		Player p = e.getPlayer();
		alreadyJoined.add(p);
		p.setOp(false);
		
		
//		PacketReader pr = new PacketReader(p);
//		pr.inject();
		
		p.setMaxHealth(20);
		p.setHealth(20);
		p.setHealthScale(20);
		
		if (firstJoin) {
			


			try {
				
				List<UUID> top5UUIDs = new ArrayList<UUID>();
				for (PlayerUUIDStats playeruuidstats : Main.getFiveBest()) {
					top5UUIDs.add(playeruuidstats.getUUID());
				}
				Location skull0Loc = new Location(Bukkit.getWorld("world"), 14, 22, 60);
				Location skull1Loc = new Location(Bukkit.getWorld("world"), 13, 22, 60);
				Location skull2Loc = new Location(Bukkit.getWorld("world"), 12, 22, 60);
				Location skull3Loc = new Location(Bukkit.getWorld("world"), 11, 22, 60);
				Location skull4Loc = new Location(Bukkit.getWorld("world"), 10, 22, 60);

				Location sign0Loc = new Location(Bukkit.getWorld("world"), 14, 21, 60);
				Location sign1Loc = new Location(Bukkit.getWorld("world"), 13, 21, 60);
				Location sign2Loc = new Location(Bukkit.getWorld("world"), 12, 21, 60);
				Location sign3Loc = new Location(Bukkit.getWorld("world"), 11, 21, 60);
				Location sign4Loc = new Location(Bukkit.getWorld("world"), 10, 21, 60);

				if (top5UUIDs.size() > 0) {

					Skull skull0 = (Skull) skull0Loc.getBlock().getState();
					skull0.setOwner(Main.getMinigame().getLastName(top5UUIDs.get(0)));
					skull0.update();

					Sign sign0 = (Sign) sign0Loc.getBlock().getState();

					sign0.setLine(0, "§2" + Main.getMinigame().getLastName(top5UUIDs.get(0)));
					sign0.setLine(1, "§aKD: " + Main.getFiveBest()[0].getKills());
					sign0.setLine(2, "§aKills: " + Main.getFiveBest()[0].getKills());
					sign0.setLine(3, "§aWins: " + Main.getFiveBest()[0].getWins());
					sign0.update();

				} else {
					Skull skull0 = (Skull) skull0Loc.getBlock().getState();
					skull0.setOwner("MHF_Question");
					skull0.update();

					Sign sign0 = (Sign) sign0Loc.getBlock().getState();

					sign0.setLine(0, "");
					sign0.setLine(1, "§c§lUnbekannt");
					sign0.setLine(2, "/");
					sign0.setLine(3, "");
					sign0.update();
				}

				//

				if (top5UUIDs.size() > 1) {

					Skull skull1 = (Skull) skull1Loc.getBlock().getState();
					skull1.setOwner(Main.getMinigame().getLastName(top5UUIDs.get(1)));
					skull1.update();

					Sign sign1 = (Sign) sign1Loc.getBlock().getState();

					sign1.setLine(0, "§2" + Main.getMinigame().getLastName(top5UUIDs.get(1)));
					sign1.setLine(1, "§aKD: " + Main.getFiveBest()[1].getKills());
					sign1.setLine(2, "§aKills: " + Main.getFiveBest()[1].getKills());
					sign1.setLine(3, "§aWins: " + Main.getFiveBest()[1].getWins());
					sign1.update();

				} else {
					Skull skull1 = (Skull) skull1Loc.getBlock().getState();
					skull1.setOwner("MHF_Question");
					skull1.update();

					Sign sign1 = (Sign) sign1Loc.getBlock().getState();

					sign1.setLine(0, "");
					sign1.setLine(1, "§c§lUnbekannt");
					sign1.setLine(2, "/");
					sign1.setLine(3, "");
					sign1.update();
				}

				//

				if (top5UUIDs.size() > 2) {

					Skull skull2 = (Skull) skull2Loc.getBlock().getState();
					skull2.setOwner(Main.getMinigame().getLastName(top5UUIDs.get(2)));
					skull2.update();

					Sign sign2 = (Sign) sign2Loc.getBlock().getState();

					sign2.setLine(0, "§2" + Main.getMinigame().getLastName(top5UUIDs.get(2)));
					sign2.setLine(1, "§aKD: " + Main.getFiveBest()[2].getKills());
					sign2.setLine(2, "§aKills: " + Main.getFiveBest()[2].getKills());
					sign2.setLine(3, "§aWins: " + Main.getFiveBest()[2].getWins());
					sign2.update();

				} else {
					Skull skull2 = (Skull) skull2Loc.getBlock().getState();
					skull2.setOwner("MHF_Question");
					skull2.update();

					Sign sign2 = (Sign) sign2Loc.getBlock().getState();

					sign2.setLine(0, "");
					sign2.setLine(1, "§c§lUnbekannt");
					sign2.setLine(2, "/");
					sign2.setLine(3, "");
					sign2.update();
				}

				//

				if (top5UUIDs.size() > 3) {

					Skull skull3 = (Skull) skull3Loc.getBlock().getState();
					skull3.setOwner(Main.getMinigame().getLastName(top5UUIDs.get(3)));
					skull3.update();

					Sign sign3 = (Sign) sign3Loc.getBlock().getState();

					sign3.setLine(0, "§2" + Main.getMinigame().getLastName(top5UUIDs.get(3)));
					sign3.setLine(1, "§aKD: " + Main.getFiveBest()[3].getKills());
					sign3.setLine(2, "§aKills: " + Main.getFiveBest()[3].getKills());
					sign3.setLine(3, "§aWins: " + Main.getFiveBest()[3].getWins());
					sign3.update();

				} else {
					Skull skull3 = (Skull) skull3Loc.getBlock().getState();
					skull3.setOwner("MHF_Question");
					skull3.update();

					Sign sign3 = (Sign) sign3Loc.getBlock().getState();

					sign3.setLine(0, "");
					sign3.setLine(1, "§c§lUnbekannt");
					sign3.setLine(2, "/");
					sign3.setLine(3, "");
					sign3.update();
				}

				//

				if (top5UUIDs.size() > 4) {

					Skull skull4 = (Skull) skull4Loc.getBlock().getState();
					skull4.setOwner(Main.getMinigame().getLastName(top5UUIDs.get(4)));
					skull4.update();

					Sign sign4 = (Sign) sign4Loc.getBlock().getState();

					sign4.setLine(0, "§2" + Main.getMinigame().getLastName(top5UUIDs.get(4)));
					sign4.setLine(1, "§aKD: " + Main.getFiveBest()[4].getKills());
					sign4.setLine(2, "§aKills: " + Main.getFiveBest()[4].getKills());
					sign4.setLine(3, "§aWins: " + Main.getFiveBest()[4].getWins());

				} else {
					Skull skull4 = (Skull) skull4Loc.getBlock().getState();
					skull4.setOwner("MHF_Question");
					skull4.update();

					Sign sign4 = (Sign) sign4Loc.getBlock().getState();

					sign4.setLine(0, "");
					sign4.setLine(1, "§c§lUnbekannt");
					sign4.setLine(2, "/");
					sign4.setLine(3, "");
					sign4.update();
				}

			} catch (IndexOutOfBoundsException ex) {
				System.out.println("Error");
				ex.printStackTrace();
			}

		
			
			firstJoin = false;
		}
		

		ScoreBoardManager.setPrefix(p);
		
		
		
		if (!Pistol.isRealoding.containsKey(p)) 
			Pistol.isRealoding.put(p, false);
		
		if (!Sniper.isRealoding.containsKey(p))
			Sniper.isRealoding.put(p, false);
		
		if (!RocketLauncher.isRealoding.containsKey(p)) {
			RocketLauncher.isRealoding.put(p, false);
		}
		if (!AK.isRealoding.containsKey(p)) {
			AK.isRealoding.put(p, false);
		}
		if (!SchrotFlinte.isRealoding.containsKey(p)) {
			SchrotFlinte.isRealoding.put(p, false);
		}
		if (!WhiteRide.isRealoding.containsKey(p)) {
			WhiteRide.isRealoding.put(p, false);
		}
		if (!MachineGun.isRealoding.containsKey(p)) {
			MachineGun.isRealoding.put(p, false);
		}
		Pistol.lastShoot.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
		AK.lastShoot.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
		SchrotFlinte.lastShoot.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
		SpectatorClass.specDelay.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
		
		AK.shootsLeft.put(p, AK.shoots);
		MachineGun.shootsLeft.put(p, MachineGun.shoots);
		Pistol.shootsLeft.put(p, Pistol.shoots);
		RocketLauncher.shootsLeft.put(p, RocketLauncher.shoots);
		Sniper.shootsLeft.put(p, Sniper.shoots);
		WhiteRide.shootsLeft.put(p, WhiteRide.shoots);
		SchrotFlinte.shootsLeft.put(p, SchrotFlinte.shoots);
		
		if (!Listener_OnBuildOutsideBorder.lastBreaked.containsKey(e.getPlayer()))
			Listener_OnBuildOutsideBorder.lastBreaked.put(e.getPlayer(), -1L);
		if (!Listener_OnBuildOutsideBorder.placedCounter.containsKey(e.getPlayer()))
			Listener_OnBuildOutsideBorder.placedCounter.put(e.getPlayer(), 0);
		
		p.getInventory().clear();
		new ClearUtil(p);
		if (!Main.isGameRunning) {
			e.setJoinMessage(Main.Prefix+"§6"+p.getName()+" §dist dem Spiel beigetreten!");
			p.setGameMode(GameMode.SURVIVAL);
			p.sendMessage(Main.Prefix+"§eWarteLobby-Map gebaut von Venterox.");
		}else {
			e.setJoinMessage("");
			p.sendMessage(Main.Prefix+"§cDas Spiel hat bereits begonnen. Warte doch auf die nächste Runde!");
			SpectatorClass.setSpectator(p, null);	
		}
		
		
			Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				p.teleport(new Location(Bukkit.getWorld("world"), 17.77017129922218, 21, 51.41904290788815, (float)90.8998031616211, (float)0.1499878466129303));
				
				if (!Main.isGameRunning) {
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta bookMeta = (BookMeta)book.getItemMeta();
				bookMeta.setDisplayName("§3§lWar§b§lGame §f- §2Anleitung");
				bookMeta.setAuthor("§4ChiliPro.net");
				bookMeta.addPage("§8[]=-=§3§lWar§b§lGame§8=-=[]\n"
							   + "§cby §4ChiliPro.net\n"
							   + "\n"
							   + "§8§nAllgemeine Infos:\n"
							   + "§2Das gesamte WarGame-System basiert auf das Spielprinzip von Fortnite. Der gesamte Quellcode vom WarGame System ist zu 100% selber"
							   + " programmiert.");
				bookMeta.addPage("§2Das Projekt beinhaltet über 4.000 Zeilen feinster Code und wurde hauptsächlich §1DevOFVictory §2in ca. einem Monat gecodet."
							   + " Die Maps (Warte Lobby+Tilted Towers) wurden von §1Venterox §2und §1SeanBits §2gebaut und von uns überarbeitet.");
				bookMeta.addPage("§2Weitere Infos und eine detailierte Waffenbeschreibung hier: https://chilipro.net/wargame/");
				
				book.setItemMeta(bookMeta);
				p.getInventory().setItem(3, book);
				
				ItemStack vote = new ItemStack(Material.NETHER_STAR);
				ItemMeta voteMeta = vote.getItemMeta();
				voteMeta.setDisplayName("§d§lVoten");
				vote.setItemMeta(voteMeta);
				p.getInventory().setItem(5, vote);
				
				ItemStack skins = new ItemStack(Material.CHEST,1);
				ItemMeta skinsMeta = skins.getItemMeta();
				skinsMeta.setDisplayName("§e§lSpind");
				skins.setItemMeta(skinsMeta);
				p.getInventory().setItem(8, skins);
				}
			}
		}, 5);
			
			if (StartWartePhase.scheduler == -1 && !Main.isGameRunning) {
				new StartWartePhase(Main.getInstance(), p, 60);
			}
			
			
	//-2.467 20 50.700
			
//			Main.npc = new NPCManager("§e§lHändler", new Location(Bukkit.getWorld("world"), -2.467, 20, 50.700, (float)0, (float)0));
//			Main.npc.spawn(p);
//			NPCManager.createNPC(p, "§e§lShop");
//			BossBar.setMessage(p, "§8» §3§lWillkommen §6§l"+p.getName()+"§3§l! §8«", 10);
			
			SpindManager.setSkinsIfNessasry(p.getUniqueId());
			
			if (!Main.isTraderSpawned) {
				Main.isTraderSpawned = true;
				
				
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						Location traderLoc = new Location(Bukkit.getWorld("world"), -2.467, 20, 50.7);
						
						Villager trader = (Villager) Bukkit.getWorld("world").spawn(traderLoc, Villager.class);
						
						EntityModifier em = new EntityModifier(trader, Main.getInstance());
						
						em.modify().setNoAI(true);
						
						((CraftVillager)trader).getHandle().b(true);
						em.modify().setCanDespawn(false);
						em.modify().setCanPickUpLoot(false);
						trader.setProfession(Profession.FARMER);
						
						
						String[] text = {"§e§lHändler"};
						
						Main.holo = new Holograms(text, traderLoc);
						Main.holo.showPlayer(p);
					}
				},20);
			
			}
	
	}
}
